import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

public final class MoveGenerator {
    private static Board board;
    private List<Move> moveHistory;

    public MoveGenerator(Board board) {
        this.board = board;
        this.moveHistory = new ArrayList<>();
    }

    public static boolean isSquareAttacked(Square to, Color pieceColor) {
        //check for pawn
        int[] pawnCaptureDirections = {-1, 1};
        int colorDirection = pieceColor == Color.WHITE ? 1 : -1;

        for (int direction : pawnCaptureDirections){
            if(board.isValidSquare(to.getRow() +  colorDirection, to.getCol() + direction)) {
                Piece piece = board.getPiece(to.getRow() +  colorDirection, to.getCol() + direction);
                if(piece instanceof Pawn && piece.getColor() != pieceColor) {
                    return true;
                }
            }
        }

        //check for knight
        int[][] knightCaptureDirections = { {2, -1}, {2, 1}, {-2, -1}, {-2, 1}, {1, -2}, {1, 2}, {-1, -2}, {-1, 2} };
        for (int[] direction : knightCaptureDirections){
            if(board.isValidSquare(to.getRow() +  direction[0], to.getCol() + direction[1])) {
                Piece piece = board.getPiece(to.getRow() +  direction[0], to.getCol() + direction[1]);
                if(piece instanceof Knight && piece.getColor() != pieceColor) {
                    return true;
                }
            }
        }

        //check for bishop/queen
        int[][] bishopCaptureDirections = { {1, 1}, {1, -1}, {-1, -1}, {-1, 1} };
        for (int[] direction : bishopCaptureDirections){
            int row = to.getRow() + direction[0];
            int col = to.getCol() + direction[1];

            while (board.isValidSquare(row, col)) {
                Piece piece = board.getPiece(row, col);
                if((piece instanceof Queen || piece instanceof Bishop) && piece.getColor() != pieceColor) {
                    return true;
                }

                if(piece != null && ((!(piece instanceof Queen) && !(piece instanceof Bishop)) || piece.getColor() == pieceColor)) {
                    break;
                }

                row += direction[0];
                col += direction[1];
            }
        }

        //check for rook/queen
        int[][] rookCaptureDirections = { {1, 0}, {0, -1}, {-1, 0}, {0, 1} };
        for (int[] direction : rookCaptureDirections){
            int row = to.getRow() + direction[0];
            int col = to.getCol() + direction[1];

            while (board.isValidSquare(row, col)) {
                Piece piece = board.getPiece(row, col);
                if((piece instanceof Queen || piece instanceof Rook) && piece.getColor() != pieceColor) {
                    return true;
                }

                if(piece != null && ((!(piece instanceof Queen) && !(piece instanceof Rook)) || piece.getColor() == pieceColor)) {
                    break;
                }

                row += direction[0];
                col += direction[1];
            }
        }

        //check for king
        int[][] kingCaptureDirections = { {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1} };
        for (int[] direction : kingCaptureDirections){
            if(board.isValidSquare(to.getRow() +  direction[0], to.getCol() + direction[1])) {
                Piece piece = board.getPiece(to.getRow() +  direction[0], to.getCol() + direction[1]);
                if(piece instanceof King && piece.getColor() != pieceColor) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isKingInCheck(Board board, Color color) {
        Square kingPosition = board.getKingPosition(color);
        return isSquareAttacked(kingPosition, color);
    }

    public void movePiece(Square from, Square to) throws IllegalMoveException, CloneNotSupportedException {
        Piece piece = this.board.getPiece(from.getRow(), from.getCol());

        if (piece == null) {
            throw new IllegalMoveException("No piece at " + from.translateSquareToNotation());
        }

        List<Move> legalMoves = this.legalMoves(from);
        boolean isLegal = false;
        Move selectedMove = null;
        boolean isKingInCheck = isKingInCheck(this.board, this.board.getSideToMove());

        for (Move move: legalMoves) {
            if (!move.getTo().equals(to)) continue;

            Board testBoard = this.board.deepCopy();
            applyMoveToBoardForValidation(testBoard, move);

            boolean kingInCheckAfter = isKingInCheck(testBoard, testBoard.getSideToMove());
            if (!kingInCheckAfter) {
                isLegal = true;
                selectedMove = move;
                break;
            }
        }

        if(isLegal) {
            applyMoveToBoardForValidation(this.board, selectedMove);
            this.addMoveToHistory(selectedMove);
            this.board.switchSide();
        } else {
            if (isKingInCheck) {
                throw new IllegalMoveException("Illegal move: " + piece.getClass().getSimpleName()
                        + " cannot move from " + from.translateSquareToNotation() + " to " + to.translateSquareToNotation() + " Your king is in check");
            }
            throw new IllegalMoveException("Illegal move: " + piece.getClass().getSimpleName()
                    + " cannot move from " + from.translateSquareToNotation() + " to " + to.translateSquareToNotation());
        }

    }

    private void applyMoveToBoardForValidation(Board boardToUse, Move move) {
        Square to = move.getTo();
        Square from = move.getFrom();
        Piece moving = boardToUse.getPiece(from.getRow(), from.getCol());

        boardToUse.setPiece(to.getRow(), to.getCol(), moving);
        boardToUse.setPiece(from.getRow(), from.getCol(), null);
        if (move.getMoveType() == MoveType.CASTLING) {
            if (to.getCol() - from.getCol() == 2) {
                Rook rook = (Rook)board.getPiece(to.getRow(),7);
                boardToUse.setPiece(to.getRow(), 5, rook);
                boardToUse.setPiece(to.getRow(), 7, null);
                ((King) moving).setHasShortCastleRights(false);
            }
            if (from.getCol() - to.getCol() == 3) {
                Rook rook = (Rook)board.getPiece(to.getRow(),0);
                boardToUse.setPiece(to.getRow(), 3, rook);
                boardToUse.setPiece(to.getRow(), 0, null);
                ((King) moving).setHasLongCastleRights(false);
            }
        }
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
            Move move = new Move(from, to, piece);

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

        if (piece == null || !(piece instanceof King)) return legalMoves;

        Color kingColor = piece.getColor();

        int[][] directions = { {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1} };

        for (int[] dir : directions) {
            int row = from.getRow() + dir[0];
            int col = from.getCol() + dir[1];
            Square to = new Square(row, col);

            if(!board.isValidSquare(row, col)) continue;

            Piece currentPiece = board.getPiece(row, col);
            boolean isCapture = currentPiece != null && currentPiece.getColor() != kingColor;

            if((currentPiece == null || isCapture) && !isSquareAttacked(to, kingColor)) {
                legalMoves.add(new Move(from, to, piece));
            }
        }

        King king = (King)piece;
        int row = from.getRow();
        int col = from.getCol();

        BiPredicate<Integer, Piece> rookAt = (file, p) -> {
            if (!board.isValidSquare(row, file)) return false;
            Piece candidate = board.getPiece(row, file);
            return (candidate instanceof Rook) && candidate.getColor() == kingColor;
        };

        if (king.getHasShortCastleRights()) {
            int f1 = col + 1;
            int f2 = col + 2;
            int rookFile = col + 3;
            if(board.isValidSquare(row, f1) && board.isValidSquare(row, f2) && board.isValidSquare(row, rookFile)) {
                if(board.getPiece(row, f1) == null && board.getPiece(row, f2) == null
                        && rookAt.test(rookFile, null)
                        && !isSquareAttacked(from, kingColor)
                        && !isSquareAttacked(new Square(row, f1), kingColor)
                        && !isSquareAttacked(new Square(row, f2), kingColor)
                ) {
                    legalMoves.add(new Move(from, new Square(row, f2), MoveType.CASTLING, king));
                }
            }
        }

        if (king.getHasLongCastleRights()) {
            int f1 = col - 1;
            int f2 = col - 2;
            int f3 = col - 3;
            int rookFile = col - 4;
            if(board.isValidSquare(row, f1) && board.isValidSquare(row, f2) && board.isValidSquare(row, f3) && board.isValidSquare(row, rookFile)) {
                if(board.getPiece(row, f1) == null && board.getPiece(row, f2) == null && board.getPiece(row, f3) == null
                        && rookAt.test(rookFile, null)
                        && !isSquareAttacked(from, kingColor)
                        && !isSquareAttacked(new Square(row, f1), kingColor)
                        && !isSquareAttacked(new Square(row, f2), kingColor)
                ) {
                    legalMoves.add(new Move(from, new Square(row, f2), MoveType.CASTLING, king));
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
                Move move = new Move(from, to, piece);

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
                Move move = new Move(from, to, piece);

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
                Move move = new Move(from, to, piece);

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
            legalMoves.add(new Move(from, firstSquare, piece));
            if(from.getRow() == startRow && piece2Square == null) {
                legalMoves.add(new Move(from, secondSquare, piece));
            }
        }

        int[] captureDir = {-1, 1};
        Move lastMove = moveHistory.size() > 0 ? moveHistory.getLast() : null;

        for (int dir : captureDir) {
            Square capture = new Square(from.getRow() + direction,from.getCol() + dir);
            // Piece nextToPiece = board.getPiece(from.getRow(),from.getCol() + dir);

            boolean canEnPassant = lastMove != null && lastMove.getPiece() instanceof Pawn
                    && Math.abs(lastMove.getFrom().getRow() - lastMove.getTo().getRow()) == 2
                    && lastMove.getPiece().getColor() != piece.getColor() && lastMove.getTo().getCol() == from.getCol() + dir;

            boolean isSquareValid = board.isValidSquare(from.getRow(), from.getCol() + dir);

            if(isSquareValid && (board.getPiece(from.getRow() + direction,from.getCol() + dir) != null)) {
                legalMoves.add(new Move(from, capture, piece));
            }
            if(isSquareValid && canEnPassant) {
                legalMoves.add(new Move(from, capture, MoveType.EN_PASSANT, piece));
            }
        }

        /*Square rightCapture = new Square(from.getRow() + direction,from.getCol() + 1);
        Square leftCapture = new Square(from.getRow() + direction, from.getCol() - 1);


        if(from.getCol() < 7 && board.getPiece(from.getRow() + direction,from.getCol() + 1) != null) {
            legalMoves.add(new Move(from, rightCapture, piece));
        }

        if(from.getCol() > 0 && board.getPiece(from.getRow() + direction, from.getCol() - 1) != null) {
            legalMoves.add(new Move(from, leftCapture, piece));
        }*/

        return legalMoves;
    }

    public List<Move> getMoveHistory() {
        return moveHistory;
    }

    private void addMoveToHistory(Move move) {
        this.moveHistory.add(move);
    }
}
