import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece{
    public Pawn(Color color) {
        super(color);
        this.setName("P");
    }

    @Override
    public List<Move> calculateLegalMoves(BoardView board, Square from) {
        List<Move> legalMoves = new ArrayList<>();

        int direction = this.getColor() == Color.WHITE ? 1 : -1;
        int startRow = this.getColor() == Color.WHITE ? 1 : 6;

        Square firstSquare = board.getSquare(from.getRow() + direction, from.getCol());
        Square secondSquare = board.getSquare(from.getRow() + 2 * direction, from.getCol());

        Piece piece1Square = firstSquare.getPiece();
        Piece piece2Square = secondSquare.getPiece();

        if(piece1Square == null) {
            legalMoves.add(new Move(from, firstSquare));
            if(from.getRow() == startRow && piece2Square == null) {
                legalMoves.add(new Move(from, secondSquare));
            }
        }

        Square rightCapture = board.getSquare(from.getRow() + direction,from.getCol() + 1);
        Square leftCapture = board.getSquare(from.getRow() + direction, from.getCol() - 1);

        if(from.getCol() < 7 && rightCapture.getPiece() != null) {
            legalMoves.add(new Move(from, rightCapture));
        }

        if(from.getCol() > 0 && leftCapture.getPiece() != null) {
            legalMoves.add(new Move(from, leftCapture));
        }

        return legalMoves;
    }
}
