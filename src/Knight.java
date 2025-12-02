import java.util.List;

public class Knight extends Piece{
    public Knight(Color color) {
        super(color);
        this.setName("N");
    }

    @Override
    public void move() {

    }

    @Override
    public List<Move> calculateLegalMoves(Board board, Square from) {
        return List.of();
    }
}

