public class Board implements BoardView {
    private Piece[][] board = new Piece[8][8];
    private Color sideToMove = Color.WHITE;
    private final String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h"};

    public Board() {
        this.initializeBoard();
    }

    public Piece[][] getBoard() {
        return board;
    }

    public void setBoard(Piece[][] board) {
        this.board = board;
    }

    public Piece getPiece(int row, int col) {
        return this.board[row][col];
    }

    public void setPiece(int row, int col, Piece piece) {
        this.board[row][col] = piece;
    }

    public boolean isValidSquare(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    public void switchSide() {
        this.sideToMove = this.sideToMove == Color.WHITE ? Color.BLACK : Color.WHITE;
    }

    public Color getSideToMove() {
        return this.sideToMove;
    }

    public boolean isCheckmate() {

        return false;
    }

    public King getKing(Color pieceColor) {
        for (Piece[] rows : this.board) {
            for (Piece piece: rows){
                if (piece instanceof King && piece.getColor() == pieceColor) {
                    return (King)piece;
                }
            }
        }
        return null;
    }

    public Square getKingPosition (Color pieceColor) {
        Piece[][] pieces = this.board;
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++){
                Piece piece = pieces[i][j];
                if (piece instanceof King && piece.getColor() == pieceColor) {
                    return new Square(i, j);
                }
            }
        }
        return null;
    }

    public boolean isSquareAttacked(Square to, Color pieceColor) {
        //check for pawn
        int[] pawnCaptureDirections = {-1, 1};
        int colorDirection = pieceColor == Color.WHITE ? 1 : -1;

        for (int direction : pawnCaptureDirections){
            if(this.isValidSquare(to.getRow() +  colorDirection, to.getCol() + direction)) {
                Piece piece = this.getPiece(to.getRow() +  colorDirection, to.getCol() + direction);
                if(piece instanceof Pawn && piece.getColor() != pieceColor) {
                    return true;
                }
            }
        }

        //check for knight
        int[][] knightCaptureDirections = { {2, -1}, {2, 1}, {-2, -1}, {-2, 1}, {1, -2}, {1, 2}, {-1, -2}, {-1, 2} };
        for (int[] direction : knightCaptureDirections){
            if(this.isValidSquare(to.getRow() +  direction[0], to.getCol() + direction[1])) {
                Piece piece = this.getPiece(to.getRow() +  direction[0], to.getCol() + direction[1]);
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

            while (this.isValidSquare(row, col)) {
                Piece piece = this.getPiece(row, col);
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

            while (this.isValidSquare(row, col)) {
                Piece piece = this.getPiece(row, col);
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
            if(this.isValidSquare(to.getRow() +  direction[0], to.getCol() + direction[1])) {
                Piece piece = this.getPiece(to.getRow() +  direction[0], to.getCol() + direction[1]);
                if(piece instanceof King && piece.getColor() != pieceColor) {
                    return true;
                }
            }
        }

        return false;
    }

    public void initializeBoard() {
        //Initialize white pieces
        this.board[0][0] = new Rook(Color.WHITE);
        this.board[0][1] = new Knight(Color.WHITE);
        this.board[0][2] = new Bishop(Color.WHITE);
        this.board[0][3] = new Queen(Color.WHITE);
        this.board[0][4] = new King(Color.WHITE);
        this.board[0][5] = new Bishop(Color.WHITE);
        this.board[0][6] = new Knight(Color.WHITE);
        this.board[0][7] = new Rook(Color.WHITE);
        this.board[1][0] = new Pawn(Color.WHITE);
        this.board[1][1] = new Pawn(Color.WHITE);
        this.board[1][2] = new Pawn(Color.WHITE);
        this.board[1][3] = new Pawn(Color.WHITE);
        this.board[1][4] = new Pawn(Color.WHITE);
        this.board[1][5] = new Pawn(Color.WHITE);
        this.board[1][6] = new Pawn(Color.WHITE);
        this.board[1][7] = new Pawn(Color.WHITE);

        //Initialize black pieces
        this.board[7][0] = new Rook(Color.BLACK);
        this.board[7][1] = new Knight(Color.BLACK);
        this.board[7][2] = new Bishop(Color.BLACK);
        this.board[7][3] = new Queen(Color.BLACK);
        this.board[7][4] = new King(Color.BLACK);
        this.board[7][5] = new Bishop(Color.BLACK);
        this.board[7][6] = new Knight(Color.BLACK);
        this.board[7][7] = new Rook(Color.BLACK);
        this.board[6][0] = new Pawn(Color.BLACK);
        this.board[6][1] = new Pawn(Color.BLACK);
        this.board[6][2] = new Pawn(Color.BLACK);
        this.board[6][3] = new Pawn(Color.BLACK);
        this.board[6][4] = new Pawn(Color.BLACK);
        this.board[6][5] = new Pawn(Color.BLACK);
        this.board[6][6] = new Pawn(Color.BLACK);
        this.board[6][7] = new Pawn(Color.BLACK);

        // initialize remaining squares without any piece
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = null;
            }
        }
    }

    public void print() {
        for (int i = this.board.length - 1; i >= 0; i--) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < this.board[i].length; j++) {
                if(this.board[i][j] == null) {
                    System.out.print("    ");
                } else {
                    System.out.print(this.board[i][j].getColor().toString().charAt(0) + "-" + this.board[i][j].getName() + " ");
                }
            }
            System.out.println();
        }
        for (int k = 0; k < this.letters.length; k++) {
            System.out.print("  ");
            System.out.print(" " + letters[k]);
        }
        System.out.println();
    }


}
