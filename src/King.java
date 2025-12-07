import java.util.ArrayList;
import java.util.List;

public class King extends Piece{
    public King(Color color) {
        super(color);
        this.setName("K");
    }

    private boolean isKingInCheck(BoardView board, Square to) {
        Square kingPosition = board.getKingPosition(this.getColor());
        return board.isSquareAttacked(kingPosition, this.getColor());
    }

    @Override
    public List<Move> calculateLegalMoves(BoardView board, Square from) {
        List<Move> legalMoves = new ArrayList<>();
        Color kingColor = this.getColor();

        int[][] directions = { {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1} };

        for (int[] dir : directions) {
            int row = from.getRow() + dir[0];
            int col = from.getCol() + dir[1];
            Square to = board.getSquare(row, col);

            if(!board.isValidSquare(row, col)){
                continue;
            }

            Piece currentPiece = board.getSquare(row, col).getPiece();
            Move move = new Move(from, to);

            if(currentPiece == null && !board.isSquareAttacked(to, this.getColor())) {
                legalMoves.add(move);
            } else if(currentPiece != null) {
                if(currentPiece.getColor() != kingColor) {
                    legalMoves.add(move);
                }
            }
        }
        return legalMoves;
    }
}
