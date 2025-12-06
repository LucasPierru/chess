import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece{
    public Rook(Color color) {
        super(color);
        this.setName("R");
    }

    @Override
    public List<Move> calculateLegalMoves(Board board, Square from) {
        List<Move> legalMoves = new ArrayList<>();

        Piece piece = board.getPiece(from);
        if (!(piece instanceof Rook)) return legalMoves;
        Rook rook = (Rook)piece;
        Color rookColor = rook.getColor();

        int[][] directions = { {0, -1}, {0, 1}, {-1, 0}, {1, 0} };

        for (int[] dir : directions) {
            int row = from.row() + dir[0];
            int col = from.col() + dir[1];

            while (board.isValidSquare(new Square(row, col))) {
                Piece currentPiece = board.getPiece(new Square(row, col));
                Square to = new Square(row, col);
                Move move = new Move(from, to);

                if(currentPiece == null) {
                    legalMoves.add(move);
                } else {
                    if(currentPiece.getColor() != rookColor) {
                        legalMoves.add(move);
                    }
                    break;
                }
                row += dir[0];
                col += dir[1];
            }
        }

        return legalMoves;
    }
}
