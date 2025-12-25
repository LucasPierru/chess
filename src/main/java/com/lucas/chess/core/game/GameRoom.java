package com.lucas.chess.core.game;

import com.lucas.chess.core.piece.Color;

import java.util.Random;

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

    public void selectColor(String playerId, Color selectedColor) {
        if(selectedColor == Color.WHITE) {
            whitePlayerId = playerId;
        } else if (selectedColor == Color.BLACK) {
            blackPlayerId = playerId;
        } else {
            Random rand = new Random();
            float random;
            random = rand.nextFloat();
            if(random > 0.5) {
                whitePlayerId = playerId;
            } else {
                blackPlayerId = playerId;
            }
        }
        numberOfPlayers++;
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

    public GameResult getGameResult() {
        if(game.isCheckmate()){
            return GameResult.CHECKMATE;
        } else if(game.isStalemate()) {
            return GameResult.STALEMATE;
        }
        return null;
    }

    public Color getWinner() {
        if(getGameResult() == null) return null;
        switch(getGameResult()){
            case RESIGNATION, CHECKMATE, TIMEOUT -> {
                return game.getSideToMove() == Color.WHITE ? Color.BLACK : Color.WHITE;
            }
            default -> {
                return null;
            }
        }
    }
}
