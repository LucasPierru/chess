package move;

import board.Square;
import piece.Piece;
import piece.PieceType;

import java.util.Objects;

public final class Move {
    private final Square from;
    private final Square to;
    private final MoveType moveType;
    private final Piece piece;
    private final PieceType promotion;
    private final String[] LETTERS = {"a", "b", "c", "d", "e", "f", "g", "h"};

    public Move(Square from, Square to, Piece piece) {
        this.from = from;
        this.to = to;
        this.moveType = MoveType.NORMAL;
        this.promotion = null;
        this.piece = piece;
    }

    public Move(Square from, Square to, MoveType moveType, Piece piece) {
        this.from = from;
        this.to = to;
        this.moveType = moveType;
        this.promotion = null;
        this.piece = piece;
    }

    public Move(Square from, Square to, MoveType moveType, PieceType promotion, Piece piece) {
        this.from = from;
        this.to = to;
        this.moveType = moveType;
        this.promotion = promotion;
        this.piece = piece;
    }

    public Square getFrom() {
        return from;
    }

    public Square getTo() {
        return to;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public Piece getPiece() {
        return piece;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return Objects.equals(from, move.from) && Objects.equals(to, move.to) && promotion == move.promotion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, promotion);
    }

    public String translateMoveToNotation() {
        return switch (getMoveType()) {
            case MoveType.NORMAL, MoveType.EN_PASSANT ->
                    (Objects.equals(piece.getName(), "P") ? "" : piece.getName()) + this.LETTERS[to.getCol()] + (to.getRow() + 1);
            case MoveType.CASTLING -> "O-O";
            case MoveType.PROMOTION -> this.LETTERS[to.getCol()] + (to.getRow() + 1) + "=" + promotion;
        };
    }

    public PieceType getPromotion() {
        return promotion;
    }
}
