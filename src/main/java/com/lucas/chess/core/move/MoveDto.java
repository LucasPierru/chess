package com.lucas.chess.core.move;

import com.lucas.chess.core.piece.PieceType;

public record MoveDto(String from, String to, PieceType promotion) {
}
