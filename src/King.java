import java.util.List;

public class King extends Piece{
    public King(Color color) {
        super(color);
        this.setName("K");
    }

    @Override
    public void move() {
        System.out.println("move");
    }

    @Override
    public List<Move> calculateLegalMoves(Board board, Square from) {
        return List.of();
    }
}
