package com.lucas.chess.core.game;

import com.lucas.chess.core.piece.Color;

public class GameRoom {
    private final Game game;
    private String whitePlayerId;
    private String blackPlayerId;
    private static int numberOfGames;
    private int numberOfPlayers;
    private int numberOfSpectators;

    public GameRoom (Game game) {
        this.game = game;
        numberOfGames++;
    }

    public boolean isSeatTaken(Color color) {
        return color == Color.WHITE
                ? whitePlayerId != null
                : blackPlayerId != null;
    }

    public Color assignPlayer(String playerId) {
        if (whitePlayerId == null) {
            whitePlayerId = playerId;
            numberOfPlayers++;
            return Color.WHITE;
        }
        if (blackPlayerId == null) {
            blackPlayerId = playerId;
            numberOfPlayers++;
            return Color.BLACK;
        }
        numberOfSpectators++;
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

    public int getNumberOfGames() {
        return numberOfGames;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public int getNumberOfSpectators() {
        return numberOfSpectators;
    }
}
