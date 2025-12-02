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
        List<Move> legalMoves = List.of();

        Piece[][] pieces = board.getBoard();

        while (startCol >= 0 && endCol < 8) {
            Piece currentSquare = pieces[startCol][from.row()];
            legalMoves.add(new Move(from, new Square(0, 0)));
        }

        return legalMoves;
    }
}
