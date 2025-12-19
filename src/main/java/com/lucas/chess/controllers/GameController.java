package com.lucas.chess.controllers;

import com.lucas.chess.core.board.Board;
import com.lucas.chess.core.board.Square;
import com.lucas.chess.core.game.Game;
import com.lucas.chess.core.move.IllegalMoveException;
import com.lucas.chess.core.move.MoveGenerator;
import com.lucas.chess.dto.BoardUpdateMessage;
import com.lucas.chess.dto.MessageType;
import com.lucas.chess.dto.MoveMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class GameController {
    private final SimpMessagingTemplate messagingTemplate;
    private final Map<String, Game> sessions = new ConcurrentHashMap<>();

    @Autowired
    public GameController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    private Game getOrCreateGame(String room) {
        return sessions.computeIfAbsent(room, r -> {
            Board board = new Board();
            MoveGenerator moveGenerator = new MoveGenerator(board);
            return new Game(moveGenerator);
        });
    }


    @MessageMapping("/game/init/{room}")
    public void init(@DestinationVariable String room) {
        Game game = getOrCreateGame(room);
        messagingTemplate.convertAndSend(
                "/topic/game/fen/" + room,
                new BoardUpdateMessage(game.encodeFen(), MessageType.MOVE_APPROVED)
        );
    }

    @MessageMapping("/game/{room}")
    public void handleMove(@DestinationVariable String room, MoveMessage move) {
        Game game = getOrCreateGame(room);
        try {
            game.movePiece(new Square(move.from()), new Square(move.to()), null);
            game.switchSide();
            messagingTemplate.convertAndSend(
                    "/topic/game/fen/" + room,
                    new BoardUpdateMessage(game.encodeFen(), MessageType.MOVE_APPROVED)
            );
        } catch (IllegalMoveException | CloneNotSupportedException e) {
            System.out.println(e.getMessage());
            messagingTemplate.convertAndSend(
                    "/topic/game/fen/" + room,
                    new BoardUpdateMessage(game.encodeFen(), MessageType.MOVE_REJECTED)
            );
        }
    }
}
