import java.util.List;

public class Queen extends Piece{
    public Queen(Color color) {
        super(color);
        this.setName("Q");
    }

    @Override
    public void move() {

    }

    @Override
    public List<Move> calculateLegalMoves(Board board, Square from) {
        return List.of();
    }
}
