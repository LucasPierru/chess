import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece{
    public Rook(Color color) {
        super(color);
        this.setName("R");
    }

    @Override
    public void move() {

    }

    @Override
    public List<Move> calculateLegalMoves(Board board, Square from) {
        int startCol = from.col() - 1;
        int endCol = from.col() + 1;
        int startRow = from.row() - 1;
        int endRow = from.row() + 1;
        List<Move> legalMoves = new ArrayList<>();
        Piece[][] pieces = board.getBoard();

        Rook rook = (Rook)pieces[from.row()][from.col()];

        while (startCol >= 0) {
            Piece currentSquare = pieces[from.row()][startCol];
            Move move = new Move(from, new Square(from.row(), startCol));
            if(currentSquare == null) {
                legalMoves.add(move);
                startCol--;
                continue;
            }
            if(currentSquare.getColor() != rook.getColor()) {
                legalMoves.add(move);
                break;
            }
            if(currentSquare.getColor() == rook.getColor()) {
                break;
            }

        }

        while (endCol < 8) {
            Piece currentSquare = pieces[from.row()][endCol];
            Move move = new Move(from, new Square(from.row(), endCol));
            if(currentSquare == null) {
                legalMoves.add(move);
                endCol++;
                continue;
            }
            if(currentSquare.getColor() != rook.getColor()) {
                legalMoves.add(move);
                break;
            }
            if(currentSquare.getColor() == rook.getColor()) {
                break;
            }
        }

        while (startRow >= 0) {
            Piece currentSquare = pieces[startRow][from.col()];
            Move move = new Move(from, new Square(startRow, from.col()));
            if(currentSquare == null) {
                legalMoves.add(move);
                startRow--;
                continue;
            }
            if(currentSquare.getColor() != rook.getColor()) {
                legalMoves.add(move);
                break;
            }
            if(currentSquare.getColor() == rook.getColor()) {
                break;
            }
        }

        while (endRow < 8) {
            Piece currentSquare = pieces[endRow][from.col()];
            Move move = new Move(from, new Square(endRow, from.col()));
            if(currentSquare == null) {
                legalMoves.add(move);
                endRow++;
                continue;
            }
            if (currentSquare.getColor() != rook.getColor()) {
                legalMoves.add(move);
                break;
            }
            if(currentSquare.getColor() == rook.getColor()) {
                break;
            }
        }

        return legalMoves;
    }
}
