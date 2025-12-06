import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece{
    public Pawn(Color color) {
        super(color);
        this.setName("P");
    }

    @Override
    public List<Move> calculateLegalMoves(Board board, Square from) {
        List<Move> legalMoves = new ArrayList<>();
        Piece piece = board.getPiece(from);

        if (!(piece instanceof Pawn)) return legalMoves;

        Pawn pawn = (Pawn)piece;


        int direction = pawn.getColor() == Color.WHITE ? 1 : -1;
        int startRow = pawn.getColor() == Color.WHITE ? 1 : 6;

        Square firstSquare = new Square(from.row() + direction, from.col());
        Square secondSquare = new Square(from.row() + 2 * direction, from.col());
        Piece piece1Square = board.getPiece(firstSquare);
        Piece piece2Square = board.getPiece(secondSquare);

        if(piece1Square == null) {
            legalMoves.add(new Move(from, firstSquare));
            if(from.row() == startRow && piece2Square == null) {
                legalMoves.add(new Move(from, secondSquare));
            }
        }

        Square rightCapture = new Square(from.row() + direction,from.col() + 1);
        Square leftCapture = new Square(from.row() + direction, from.col() - 1);

        if(from.col() < 7 && board.getPiece(rightCapture) != null) {
            legalMoves.add(new Move(from, rightCapture));
        }

        if(from.col() > 0 && board.getPiece(leftCapture) != null) {
            legalMoves.add(new Move(from, leftCapture));
        }

        return legalMoves;
    }
}
