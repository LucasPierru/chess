import java.util.List;

public class Pawn extends Piece{
    public Pawn(Color color) {
        super(color);
        this.setName("P");
    }

    @Override
    public void move() {

    }

    @Override
    public List<Move> calculateLegalMoves(Board board, Square from) {
        return List.of();
    }
}
