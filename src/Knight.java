import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece{
    public Knight(Color color) {
        super(color);
        this.setName("N");
    }

    @Override
    public void move() {

    }

    @Override
    public List<Move> calculateLegalMoves(Board board, Square from) {
        List<Move> legalMoves = new ArrayList<>();

        Piece piece = board.getPiece(from);
        if (!(piece instanceof Knight)) return legalMoves;
        Knight knight = (Knight)piece;
        Color knightColor = knight.getColor();

        int[][] directions = { {2, -1}, {2, 1}, {-2, -1}, {-2, 1}, {1, -2}, {1, 2}, {-1, -2}, {-1, 2} };

        for (int[] dir : directions) {
            int row = from.row() + dir[0];
            int col = from.col() + dir[1];
            Square to = new Square(row, col);

            if(!board.isValidSquare(to)){
                continue;
            }

            Piece currentPiece = board.getPiece(to);
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

