package com.lucas.chess.dto;

import com.lucas.chess.core.piece.Color;

public record GameInitMessage(int minutes, int increment, Color color) {
}
