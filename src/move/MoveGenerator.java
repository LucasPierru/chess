package move;

import board.Board;
import board.Square;
import piece.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

public final class MoveGenerator {
    private static Board board;
    private final List<Move> moveHistory;

    public MoveGenerator(Board board) {
        MoveGenerator.board = board;
        this.moveHistory = new ArrayList<>();
    }

    public void movePiece(Square from, Square to, PieceType promotion, Color sideToMove) throws IllegalMoveException, CloneNotSupportedException {
        Piece piece = board.getPiece(from.getRow(), from.getCol());

        if (piece == null) {
            throw new IllegalMoveException("No piece at " + from.translateSquareToNotation());
        }

        if(piece.getColor() != sideToMove) {
            throw new IllegalMoveException("Illegal Move: " + sideToMove + " to move");
        }

        List<Move> legalMoves = this.legalMoves(from, sideToMove);
        boolean isLegal = false;
        Move selectedMove = null;
        boolean isKingInCheck = board.isKingInCheck(sideToMove);

        for (Move move: legalMoves) {
            if (!move.getTo().equals(to) || move.getPromotion() != promotion) continue;

            Board testBoard = board.deepCopy();
            applyMoveToBoardForValidation(testBoard, move);

            boolean kingInCheckAfter = testBoard.isKingInCheck(sideToMove);
            if (!kingInCheckAfter) {
                isLegal = true;
                selectedMove = move;
                break;
            }
        }

        if(isLegal) {
            applyMoveToBoardForValidation(board, selectedMove);
            this.addMoveToHistory(selectedMove);
        } else {
            if (isKingInCheck) {
                throw new IllegalMoveException("Illegal move: " + piece.getClass().getSimpleName()
                        + " cannot move from " + from.translateSquareToNotation() + " to " + to.translateSquareToNotation() + " Your king is in check");
            }
            throw new IllegalMoveException("Illegal move: " + piece.getClass().getSimpleName()
                    + " cannot move from " + from.translateSquareToNotation() + " to " + to.translateSquareToNotation());
        }
    }

    public void printBoard() {
        board.print();
    }

    public void printHistory () {
        List<Move> moveHistory = getMoveHistory();

        for (Move move: moveHistory){
            if (moveHistory.indexOf(move) % 2 == 0) {
                System.out.print((moveHistory.indexOf(move) + 1) + ". " + move.translateMoveToNotation() + " ");
            } else {
                System.out.println(move.translateMoveToNotation() + ";");
            }
        }
    }

    private void applyMoveToBoardForValidation(Board boardToUse, Move move) {
        Square to = move.getTo();
        Square from = move.getFrom();
        Piece moving = move.getMoveType() == MoveType.PROMOTION
                ? getPromotingPiece(move.getPromotion(), move.getPiece().getColor())
                : boardToUse.getPiece(from.getRow(), from.getCol());

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

        if (move.getMoveType() == MoveType.EN_PASSANT) {
            int dir = move.getPiece().getColor() == Color.WHITE ? 1 : -1;
            boardToUse.setPiece(to.getRow() - dir, to.getCol(), null);
        }
    }

    public boolean hasLegalMoves(Color sideToMove) {
        List<Square> squares = board.getSideSquares(sideToMove);

        for (Square square : squares) {
            for (Move move: legalMoves(square, sideToMove)) {
                if (!move.getTo().equals(square)) continue;
                Board testBoard = board.deepCopy();
                applyMoveToBoardForValidation(testBoard, move);
                boolean kingInCheckAfter = testBoard.isKingInCheck(sideToMove);
                if (!kingInCheckAfter) {
                    return true;
                }
            }
        }

        return false;
    }

    public Board getBoard () {
        return board;
    }

    private Piece getPromotingPiece (PieceType pieceType, Color color) {
        switch (pieceType) {
            case PieceType.ROOK -> {
                return new Rook(color);
            }
            case PieceType.BISHOP -> {
                return new Bishop(color);
            }
            case PieceType.QUEEN -> {
                return new Queen(color);
            }
            case PieceType.KNIGHT -> {
                return new Knight(color);
            }
            default -> {
                return null;
            }
        }
    }

    public List<Move> legalMoves(Square from, Color sideToMove) {
        Piece piece = board.getPiece(from.getRow(), from.getCol());
        if (piece == null || piece.getColor() != sideToMove) return List.of();
        return switch (piece.getClass().getName()) {
            case "piece.Pawn" -> generatePawnMoves(from);
            case "piece.King" -> generateKingMoves(from);
            case "piece.Queen" -> generateQueenMoves(from);
            case "piece.Bishop" -> generateBishopMoves(from);
            case "piece.Knight" -> generateKnightMoves(from);
            case "piece.Rook" -> generateRookMoves(from);
            default -> List.of();
        };
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

        if (!(piece instanceof King king)) return legalMoves;

        Color kingColor = piece.getColor();

        int[][] directions = { {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1} };

        for (int[] dir : directions) {
            int row = from.getRow() + dir[0];
            int col = from.getCol() + dir[1];
            Square to = new Square(row, col);

            if(!board.isValidSquare(row, col)) continue;

            Piece currentPiece = board.getPiece(row, col);
            boolean isCapture = currentPiece != null && currentPiece.getColor() != kingColor;

            if((currentPiece == null || isCapture) && !board.isSquareAttacked(to, kingColor)) {
                legalMoves.add(new Move(from, to, piece));
            }
        }

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
                        && !board.isSquareAttacked(from, kingColor)
                        && !board.isSquareAttacked(new Square(row, f1), kingColor)
                        && !board.isSquareAttacked(new Square(row, f2), kingColor)
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
                        && !board.isSquareAttacked(from, kingColor)
                        && !board.isSquareAttacked(new Square(row, f1), kingColor)
                        && !board.isSquareAttacked(new Square(row, f2), kingColor)
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

        int[] forwardDir = {1, 2};

        for (int dir: forwardDir) {
            Square forwardSquare = new Square(from.getRow() + dir * direction, from.getCol());
            Piece forwardPiece = board.getPiece(from.getRow() + dir * direction, from.getCol());
            MoveType moveType = from.getRow() + direction == 7 || from.getRow() + direction == 0 ? MoveType.PROMOTION : MoveType.NORMAL;

            if(forwardPiece == null && dir == 1) {
                getPawnMove(from, legalMoves, piece, forwardSquare, moveType);
            }

            if(forwardPiece == null && from.getRow() == startRow && dir == 2) {
                legalMoves.add(new Move(from, forwardSquare, moveType, piece));
            }
        }

        int[] captureDir = {-1, 1};
        Move lastMove = !moveHistory.isEmpty() ? moveHistory.getLast() : null;

        for (int dir : captureDir) {
            Square capture = new Square(from.getRow() + direction,from.getCol() + dir);

            boolean canEnPassant = lastMove != null && lastMove.getPiece() instanceof Pawn
                    && Math.abs(lastMove.getFrom().getRow() - lastMove.getTo().getRow()) == 2
                    && lastMove.getPiece().getColor() != piece.getColor() && lastMove.getTo().getCol() == from.getCol() + dir;

            boolean isSquareValid = board.isValidSquare(from.getRow(), from.getCol() + dir);

            if(isSquareValid && (board.getPiece(from.getRow() + direction,from.getCol() + dir) != null)) {
                MoveType moveType = from.getRow() + direction == 7 || from.getRow() + direction == 0 ? MoveType.PROMOTION : MoveType.NORMAL;
                getPawnMove(from, legalMoves, piece, capture, moveType);
            }
            if(isSquareValid && canEnPassant) {
                legalMoves.add(new Move(from, capture, MoveType.EN_PASSANT, piece));
            }
        }

        return legalMoves;
    }

    private void getPawnMove(Square from, List<Move> legalMoves, Piece piece, Square forwardSquare, MoveType moveType) {
        if(moveType == MoveType.NORMAL) {
            legalMoves.add(new Move(from, forwardSquare, moveType, piece));
        } else {
            legalMoves.add(new Move(from, forwardSquare, moveType, PieceType.BISHOP, piece));
            legalMoves.add(new Move(from, forwardSquare, moveType, PieceType.QUEEN, piece));
            legalMoves.add(new Move(from, forwardSquare, moveType, PieceType.KNIGHT, piece));
            legalMoves.add(new Move(from, forwardSquare, moveType, PieceType.ROOK, piece));
        }
    }

    public List<Move> getMoveHistory() {
        return moveHistory;
    }

    private void addMoveToHistory(Move move) {
        this.moveHistory.add(move);
    }
}
