package move;

import piece.PieceType;

public record MoveDto(String from, String to, PieceType promotion) {
}
