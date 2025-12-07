import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece{
    public Bishop(Color color) {
        super(color);
        this.setName("B");
    }

    @Override
    public List<Move> calculateLegalMoves(BoardView board, Square from) {
        List<Move> legalMoves = new ArrayList<>();

        Color bishopColor = this.getColor();

        int[][] directions = { {1, -1}, {1, 1}, {-1, 1}, {-1, -1} };

        for (int[] dir : directions) {
            int row = from.getRow() + dir[0];
            int col = from.getCol() + dir[1];

            while (board.isValidSquare(row, col)) {
                Square to = board.getSquare(row, col);
                Piece currentPiece = to.getPiece();
                Move move = new Move(from, to);

                if(currentPiece == null) {
                    legalMoves.add(move);
                } else {
                    if(currentPiece.getColor() != bishopColor) {
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
