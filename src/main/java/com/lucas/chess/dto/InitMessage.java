package com.lucas.chess.dto;

public record InitMessage(String fen, int numberOfPlayers, int numberOfSpectators) {
}
