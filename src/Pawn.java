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

        if(pawn.getColor() == Color.WHITE && pieces[from.row() + 1][from.col()] == null) {
            legalMoves.add(new Move(from, new Square(from.row() + 1, from.col())));
            if(from.row() == 1 && pieces[from.row() + 2][from.col()] == null) {
                legalMoves.add(new Move(from, new Square(from.row() + 2, from.col())));
            }
        }

        if(pawn.getColor() == Color.WHITE && from.col() < 7 && pieces[from.row() + 1][from.col() + 1] != null) {
            legalMoves.add(new Move(from, new Square(from.row() + 1, from.col() + 1)));
        }

        if(pawn.getColor() == Color.WHITE && from.col() > 0 && pieces[from.row() + 1][from.col() - 1] != null) {
            legalMoves.add(new Move(from, new Square(from.row() + 1, from.col() - 1)));
        }

        if(pawn.getColor() == Color.BLACK && pieces[from.row() - 1][from.col()] == null) {
            legalMoves.add(new Move(from, new Square(from.row() - 1, from.col())));
            if(from.row() == 6 && pieces[from.row() - 2][from.col()] == null) {
                legalMoves.add(new Move(from, new Square(from.row() - 2, from.col())));
            }
        }

        if(pawn.getColor() == Color.BLACK && from.col() < 7 && pieces[from.row() - 1][from.col() + 1] != null) {
            legalMoves.add(new Move(from, new Square(from.row() - 1, from.col() + 1)));
        }

        if(pawn.getColor() == Color.BLACK && from.col() > 0 && pieces[from.row() - 1][from.col() - 1] != null) {
            legalMoves.add(new Move(from, new Square(from.row() - 1, from.col() - 1)));
        }

        return legalMoves;
    }
}
