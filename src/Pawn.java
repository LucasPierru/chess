import java.util.ArrayList;
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
        List<Move> legalMoves = new ArrayList<>();
        Piece[][] pieces = board.getBoard();
        Pawn pawn = (Pawn)pieces[from.row()][from.col()];

        if (pawn == null) return legalMoves;

        int direction = pawn.getColor() == Color.WHITE ? 1 : -1;
        int startRow = pawn.getColor() == Color.WHITE ? 1 : 6;

        if(pieces[from.row() + direction][from.col()] == null) {
            legalMoves.add(new Move(from, new Square(from.row() + direction, from.col())));
            if(from.row() == startRow && pieces[from.row() + 2 * direction][from.col()] == null) {
                legalMoves.add(new Move(from, new Square(from.row() + 2 * direction, from.col())));
            }
        }

        if(from.col() < 7 && pieces[from.row() + direction][from.col() + 1] != null) {
            legalMoves.add(new Move(from, new Square(from.row() + direction, from.col() + 1)));
        }

        if(from.col() > 0 && pieces[from.row() + direction][from.col() - 1] != null) {
            legalMoves.add(new Move(from, new Square(from.row() + direction, from.col() - 1)));
        }

        return legalMoves;
    }
}
