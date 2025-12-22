package com.lucas.chess.dto;

import com.lucas.chess.core.piece.Color;

public record PlayerJoinMessage(Color color, boolean isPlayer, int numberOfPlayers, int numberOfSpectators) {
}
