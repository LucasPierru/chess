import java.util.List;

public class Bishop extends Piece{
    public Bishop(Color color) {
        super(color);
        this.setName("B");
    }

    @Override
    public void move() {

    }

    @Override
    public List<Move> calculateLegalMoves(Board board, Square from) {
        return List.of();
    }


}
