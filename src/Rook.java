import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece{
    private int[][] DIRECTIONS = { {0, -1}, {0, 1}, {-1, 0}, {1, 0} };
    public Rook(Color color) {
        super(color);
        this.setName("R");
    }

    @Override
    public List<Move> calculateLegalMoves(BoardView board, Square from) {
        List<Move> legalMoves = new ArrayList<>();

        Color rookColor = this.getColor();

        for (int[] dir : DIRECTIONS) {
            int row = from.getRow() + dir[0];
            int col = from.getCol() + dir[1];

            while (board.isValidSquare(row, col)) {
                Square to = board.getSquare(row, col);
                Piece currentPiece = to.getPiece();
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

    public int[][] getDirections() {
        return this.DIRECTIONS;
    }
}
