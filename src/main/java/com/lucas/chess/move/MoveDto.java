package com.lucas.chess.move;

import com.lucas.chess.piece.PieceType;

public record MoveDto(String from, String to, PieceType promotion) {
}
