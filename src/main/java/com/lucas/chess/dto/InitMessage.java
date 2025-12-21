package com.lucas.chess.dto;

import com.lucas.chess.core.piece.Color;

public record InitMessage(String fen, Color color, boolean isPlayer, int numberOfPlayers, int numberOfSpectators) {
}
