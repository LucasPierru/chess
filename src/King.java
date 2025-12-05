import java.util.ArrayList;
import java.util.List;

public class King extends Piece{
    public King(Color color) {
        super(color);
        this.setName("K");
    }

    @Override
    public void move() {
        System.out.println("move");
    }

    public boolean isKingInCheck(Board board, Square to) {
        //check for pawn
        int[] pawnCaptureDirections = {-1, 1};
        int colorDirection = this.getColor() == Color.WHITE ? 1 : -1;

        for (int direction : pawnCaptureDirections){
            Square capture = new Square(to.row() +  colorDirection, to.col() + direction);
            if(board.isValidSquare(capture)) {
                Piece piece = board.getPiece(capture);
                if(piece instanceof Pawn && piece.getColor() != this.getColor()) {
                    return true;
                }
            }
        }

        //check for knight
        int[][] knightCaptureDirections = { {2, -1}, {2, 1}, {-2, -1}, {-2, 1}, {1, -2}, {1, 2}, {-1, -2}, {-1, 2} };
        for (int[] direction : knightCaptureDirections){
            Square capture = new Square(to.row() +  direction[0], to.col() + direction[1]);
            if(board.isValidSquare(capture)) {
                Piece piece = board.getPiece(capture);
                if(piece instanceof Knight && piece.getColor() != this.getColor()) {
                    return true;
                }
            }
        }

        //check for bishop/queen
        int[][] bishopCaptureDirections = { {1, 1}, {1, -1}, {-1, -1}, {-1, 1} };
        for (int[] direction : bishopCaptureDirections){
            int row = to.row() + direction[0];
            int col = to.col() + direction[1];

            while (board.isValidSquare(new Square(row, col))) {
                Piece piece = board.getPiece(new Square(row, col));
                if((piece instanceof Queen || piece instanceof Bishop) && piece.getColor() != this.getColor()) {
                    return true;
                }

                if(piece != null && ((!(piece instanceof Queen) && !(piece instanceof Bishop)) || piece.getColor() == this.getColor())) {
                    break;
                }

                row += direction[0];
                col += direction[1];
            }
        }

        //check for rook/queen
        int[][] rookCaptureDirections = { {1, 0}, {0, -1}, {-1, 0}, {0, 1} };
        for (int[] direction : rookCaptureDirections){
            int row = to.row() + direction[0];
            int col = to.col() + direction[1];

            while (board.isValidSquare(new Square(row, col))) {
                Piece piece = board.getPiece(new Square(row, col));
                if((piece instanceof Queen || piece instanceof Rook) && piece.getColor() != this.getColor()) {
                    return true;
                }

                if(piece != null && ((!(piece instanceof Queen) && !(piece instanceof Rook)) || piece.getColor() == this.getColor())) {
                    break;
                }

                row += direction[0];
                col += direction[1];
            }
        }

        //check for king
        int[][] kingCaptureDirections = { {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1} };
        for (int[] direction : kingCaptureDirections){
            Square capture = new Square(to.row() +  direction[0], to.col() + direction[1]);
            if(board.isValidSquare(capture)) {
                Piece piece = board.getPiece(capture);
                if(piece instanceof King && piece.getColor() != this.getColor()) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public List<Move> calculateLegalMoves(Board board, Square from) {
        List<Move> legalMoves = new ArrayList<>();

        Piece piece = board.getPiece(from);
        if (!(piece instanceof King)) return legalMoves;
        King king = (King)piece;
        Color kingColor = king.getColor();

        int[][] directions = { {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1} };

        for (int[] dir : directions) {
            int row = from.row() + dir[0];
            int col = from.col() + dir[1];
            Square to = new Square(row, col);

            if(!board.isValidSquare(to)){
                continue;
            }

            Piece currentPiece = board.getPiece(to);
            Move move = new Move(from, to);

            if(currentPiece == null && !this.isKingInCheck(board, to)) {
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
