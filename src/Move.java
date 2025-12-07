import java.util.Objects;

public final class Move {
    public final Square from;
    public final Square to;
    public final PieceType promotion;

    public Move(Square from, Square to) {
        this.from = from;
        this.to = to;
        this.promotion = null;
    }

    public Square getFrom() {
        return from;
    }

    public Square getTo() {
        return to;
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
}
