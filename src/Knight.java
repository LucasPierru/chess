import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece{
    public Knight(Color color) {
        super(color);
        this.setName("N");
    }

    @Override
    public List<Move> calculateLegalMoves(BoardView board, Square from) {
        List<Move> legalMoves = new ArrayList<>();
        Color knightColor = this.getColor();

        int[][] directions = { {2, -1}, {2, 1}, {-2, -1}, {-2, 1}, {1, -2}, {1, 2}, {-1, -2}, {-1, 2} };

        for (int[] dir : directions) {
            int row = from.getRow() + dir[0];
            int col = from.getCol() + dir[1];
            Square to = board.getSquare(row, col);

            if(!board.isValidSquare(row, col)){
                continue;
            }

            Piece currentPiece = board.getSquare(row, col).getPiece();
            Move move = new Move(from, to);

            if(currentPiece == null) {
                legalMoves.add(move);
            } else {
                if(currentPiece.getColor() != knightColor) {
                    legalMoves.add(move);
                }
            }
        }
        return legalMoves;
    }
}

