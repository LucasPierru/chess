package com.lucas.chess.controllers;

import com.lucas.chess.core.board.Board;
import com.lucas.chess.core.board.Square;
import com.lucas.chess.core.game.Game;
import com.lucas.chess.core.game.GameRoom;
import com.lucas.chess.core.move.IllegalMoveException;
import com.lucas.chess.core.move.MoveGenerator;
import com.lucas.chess.core.piece.Color;
import com.lucas.chess.dto.BoardUpdateMessage;
import com.lucas.chess.dto.InitMessage;
import com.lucas.chess.dto.MessageType;
import com.lucas.chess.dto.MoveMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class GameController {
    private final SimpMessagingTemplate messagingTemplate;
    private final Map<String, GameRoom> sessions = new ConcurrentHashMap<>();

    @Autowired
    public GameController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    private GameRoom getOrCreateGame(String room) {
        return sessions.computeIfAbsent(room, r -> {
            Board board = new Board();
            MoveGenerator moveGenerator = new MoveGenerator(board);
            return new GameRoom(new Game(moveGenerator));
        });
    }

    @MessageMapping("/game/init/{room}")
    public void init(@DestinationVariable String room, Principal principal) {
        String playerId = principal.getName();
        GameRoom gameRoom = getOrCreateGame(room);

        System.out.println("Principal = " + principal.getName());

        Color color = gameRoom.getColor(playerId);

        if(color == null) {
            gameRoom.assignPlayer(playerId);
            color = gameRoom.getColor(playerId);
        }

        messagingTemplate.convertAndSendToUser(
                principal.getName(),
                "/queue/game/init",
                new InitMessage(gameRoom.encodeFen(), color, color != null)
        );
    }

    @MessageMapping("/game/{room}")
    public void handleMove(@DestinationVariable String room, MoveMessage move, Principal principal) {
        String playerId = principal.getName();
        GameRoom gameRoom = getOrCreateGame(room);

        Color playerColor = gameRoom.getColor(playerId);

        if(playerColor == null) return;

        try {
            if(gameRoom.getGame().getSideToMove() != playerColor) throw new IllegalMoveException("Wrong side");
            gameRoom.getGame().movePiece(new Square(move.from()), new Square(move.to()), null);
            gameRoom.getGame().switchSide();
            messagingTemplate.convertAndSend(
                    "/topic/game/fen/" + room,
                    new BoardUpdateMessage(gameRoom.encodeFen(), MessageType.MOVE_APPROVED)
            );
        } catch (IllegalMoveException | CloneNotSupportedException e) {
            System.out.println(e.getMessage());
            messagingTemplate.convertAndSendToUser(
                    playerId,
                    "/topic/game/fen/" + room,
                    new BoardUpdateMessage(gameRoom.encodeFen(), MessageType.MOVE_REJECTED)
            );
        }
    }
}
