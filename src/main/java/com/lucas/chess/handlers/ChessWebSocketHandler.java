package com.lucas.chess.handlers;

import com.lucas.chess.core.board.Board;
import com.lucas.chess.core.game.Game;
import com.lucas.chess.core.move.MoveGenerator;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChessWebSocketHandler extends TextWebSocketHandler {
    private final Map<String, Game> sessions = new ConcurrentHashMap<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Board board = new Board();
        MoveGenerator moveGenerator = new MoveGenerator(board);
        Game game = sessions.computeIfAbsent(session.getId(), id -> new Game(moveGenerator));
        session.sendMessage(message);
    }
}
