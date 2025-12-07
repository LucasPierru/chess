import java.util.ArrayList;
import java.util.List;

public class MoveGenerator {
    private Board board;

    public MoveGenerator(Board board) {
        this.board = board;
    }

    public void movePiece(Square from, Square to) throws IllegalMoveException {
        Piece piece = board.getPiece(from.getRow(), from.getCol());

        if (piece == null) {
            throw new IllegalMoveException("No piece at " + from.getRow() + ", " + from.getCol());
        }

        List<Move> legalMoves = this.legalMoves(from);

        for (Move move: legalMoves) {
            if (move.getTo().equals(to)) {
                board.setPiece(to.getRow(), to.getCol(), piece);
                board.setPiece(from.getRow(), from.getCol(), null);
                board.switchSide();
                return;
            }
        }

        throw new IllegalMoveException("Illegal move: " + piece.getClass().getSimpleName()
                + " cannot move from " + from + " to " + to);
    }

    public List<Move> legalMoves(Square from) {
        Piece piece = board.getPiece(from.getRow(), from.getCol());
        if (piece == null) return List.of();
        switch(piece.getClass().getName()) {
            case "Pawn": return generatePawnMoves(from);
            case "King": return generateKingMoves(from);
            case "Queen": return generateQueenMoves(from);
            case "Bishop": return generateBishopMoves(from);
            case "Knight": return generateKnightMoves(from);
            case "Rook": return generateRookMoves(from);
            default: return List.of();
        }
    }

    private List<Move> generateKnightMoves(Square from) {
        List<Move> legalMoves = new ArrayList<>();
        Piece piece = board.getPiece(from.getRow(), from.getCol());
        Color knightColor = piece.getColor();

        int[][] directions = { {2, -1}, {2, 1}, {-2, -1}, {-2, 1}, {1, -2}, {1, 2}, {-1, -2}, {-1, 2} };

        for (int[] dir : directions) {
            int row = from.getRow() + dir[0];
            int col = from.getCol() + dir[1];
            Square to = new Square(row, col);

            if(!board.isValidSquare(row, col)){
                continue;
            }

            Piece currentPiece = board.getPiece(row, col);
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

    private List<Move> generateKingMoves(Square from) {
        List<Move> legalMoves = new ArrayList<>();
        Piece piece = board.getPiece(from.getRow(), from.getCol());
        Color kingColor = piece.getColor();

        int[][] directions = { {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1} };

        for (int[] dir : directions) {
            int row = from.getRow() + dir[0];
            int col = from.getCol() + dir[1];
            Square to = new Square(row, col);

            if(!board.isValidSquare(row, col)){
                continue;
            }

            Piece currentPiece = board.getPiece(row, col);
            Move move = new Move(from, to);

            if(currentPiece == null && !board.isSquareAttacked(to, kingColor)) {
                legalMoves.add(move);
            } else if(currentPiece != null) {
                if(currentPiece.getColor() != kingColor) {
                    legalMoves.add(move);
                }
            }
        }
        return legalMoves;
    }

    private List<Move> generateBishopMoves(Square from) {
        List<Move> legalMoves = new ArrayList<>();
        Piece piece = board.getPiece(from.getRow(), from.getCol());
        Color bishopColor = piece.getColor();

        int[][] directions = { {1, -1}, {1, 1}, {-1, 1}, {-1, -1} };

        for (int[] dir : directions) {
            int row = from.getRow() + dir[0];
            int col = from.getCol() + dir[1];

            while (board.isValidSquare(row, col)) {
                Square to = new Square(row, col);
                Piece currentPiece = board.getPiece(row, col);
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

    private List<Move> generatePawnMoves(Square from) {
        List<Move> legalMoves = new ArrayList<>();
        Piece piece = board.getPiece(from.getRow(), from.getCol());
        Color pawnColor = piece.getColor();

        int direction = pawnColor == Color.WHITE ? 1 : -1;
        int startRow = pawnColor == Color.WHITE ? 1 : 6;

        Square firstSquare = new Square(from.getRow() + direction, from.getCol());
        Square secondSquare = new Square(from.getRow() + 2 * direction, from.getCol());

        Piece piece1Square = board.getPiece(from.getRow() + direction, from.getCol());
        Piece piece2Square = board.getPiece(from.getRow() + 2 * direction, from.getCol());

        if(piece1Square == null) {
            legalMoves.add(new Move(from, firstSquare));
            if(from.getRow() == startRow && piece2Square == null) {
                legalMoves.add(new Move(from, secondSquare));
            }
        }

        Square rightCapture = new Square(from.getRow() + direction,from.getCol() + 1);
        Square leftCapture = new Square(from.getRow() + direction, from.getCol() - 1);

        if(from.getCol() < 7 && board.getPiece(from.getRow() + direction,from.getCol() + 1) != null) {
            legalMoves.add(new Move(from, rightCapture));
        }

        if(from.getCol() > 0 && board.getPiece(from.getRow() + direction, from.getCol() - 1) != null) {
            legalMoves.add(new Move(from, leftCapture));
        }

        return legalMoves;
    }

    private List<Move> generateQueenMoves(Square from) {
        List<Move> legalMoves = new ArrayList<>();
        Piece piece = board.getPiece(from.getRow(), from.getCol());
        Color queenColor = piece.getColor();

        int[][] directions = { {0, -1}, {0, 1}, {-1, 0}, {1, 0}, {1, -1}, {1, 1}, {-1, 1}, {-1, -1} };

        for (int[] dir : directions) {
            int row = from.getRow() + dir[0];
            int col = from.getCol() + dir[1];

            while (board.isValidSquare(row, col)) {
                Square to = new Square(row, col);
                Piece currentPiece = board.getPiece(row, col);
                Move move = new Move(from, to);

                if(currentPiece == null) {
                    legalMoves.add(move);
                } else {
                    if(currentPiece.getColor() != queenColor) {
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

    private List<Move> generateRookMoves(Square from) {
        List<Move> legalMoves = new ArrayList<>();
        Piece piece = board.getPiece(from.getRow(), from.getCol());
        Color rookColor = piece.getColor();

        int[][] directions = { {0, -1}, {0, 1}, {-1, 0}, {1, 0} };

        for (int[] dir : directions) {
            int row = from.getRow() + dir[0];
            int col = from.getCol() + dir[1];

            while (board.isValidSquare(row, col)) {
                Square to = new Square(row, col);
                Piece currentPiece = board.getPiece(col, row);
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
