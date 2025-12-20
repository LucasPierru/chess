package com.lucas.chess.core.game;

import com.lucas.chess.core.piece.Color;

public class GameRoom {
    private final Game game;
    private String whitePlayerId;
    private String blackPlayerId;

    public GameRoom (Game game) {
        this.game = game;
    }

    public boolean isSeatTaken(Color color) {
        return color == Color.WHITE
                ? whitePlayerId != null
                : blackPlayerId != null;
    }

    public Color assignPlayer(String playerId) {
        if (whitePlayerId == null) {
            whitePlayerId = playerId;
            return Color.WHITE;
        }
        if (blackPlayerId == null) {
            blackPlayerId = playerId;
            return Color.BLACK;
        }
        return null; // spectator
    }

    public Color getColor(String playerId) {
        if(playerId.equals(whitePlayerId)) return Color.WHITE;
        if(playerId.equals(blackPlayerId)) return Color.BLACK;
        return null;
    }

    public String encodeFen() {
        return game.encodeFen();
    }

    public Game getGame() {
        return game;
    }
}
