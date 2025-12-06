import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece{
    public Bishop(Color color) {
        super(color);
        this.setName("B");
    }

    @Override
    public List<Move> calculateLegalMoves(Board board, Square from) {
        List<Move> legalMoves = new ArrayList<>();

        Piece piece = board.getPiece(from);
        if (!(piece instanceof Bishop)) return legalMoves;
        Bishop bishop = (Bishop)piece;
        Color bishopColor = bishop.getColor();

        int[][] directions = { {1, -1}, {1, 1}, {-1, 1}, {-1, -1} };

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
