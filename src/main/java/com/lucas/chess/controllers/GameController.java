package com.lucas.chess.controllers;

import com.lucas.chess.core.board.Board;
import com.lucas.chess.core.game.Game;
import com.lucas.chess.core.move.MoveGenerator;
import com.lucas.chess.core.piece.Color;
import com.lucas.chess.dto.BoardUpdateMessage;
import com.lucas.chess.dto.MoveMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {
    private final SimpMessagingTemplate messagingTemplate;
    private final Board board = new Board();
    private final MoveGenerator moveGenerator = new MoveGenerator(board);
    private final Game game = new Game(moveGenerator);

    @Autowired
    public GameController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/game/{room}")
    public void handleMove(@DestinationVariable String room, MoveMessage move) {
        String fen = moveGenerator.convertToFEN(Color.WHITE);
        messagingTemplate.convertAndSend(
                "/topic/game/fen/" + room,
                new BoardUpdateMessage(fen)
        );
    }
}
