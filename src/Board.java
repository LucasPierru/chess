public class Board {
    private Piece[][] board = new Piece[8][8];
    private Color sideToMove;
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

    public Piece getPiece(Square square) {
        return this.board[square.row()][square.col()];
    }

    public void setPiece(Square square, Piece piece) {
        this.board[square.row()][square.col()] = piece;
    }

    public void initializeBoard() {
        //Initialize white pieces
        setPiece(new Square(0, 0), new Rook(Color.WHITE));
        setPiece(new Square(0, 1), new Knight(Color.WHITE));
        setPiece(new Square(0, 2), new Bishop(Color.WHITE));
        setPiece(new Square(0, 3), new Queen(Color.WHITE));
        setPiece(new Square(0, 4), new King(Color.WHITE));
        setPiece(new Square(0, 5), new Bishop(Color.WHITE));
        setPiece(new Square(0, 6), new Knight(Color.WHITE));
        setPiece(new Square(0, 7), new Rook(Color.WHITE));
        setPiece(new Square(1, 0), new Pawn(Color.WHITE));
        setPiece(new Square(1, 1), new Pawn(Color.WHITE));
        setPiece(new Square(1, 2), new Pawn(Color.WHITE));
        setPiece(new Square(1, 3), new Pawn(Color.WHITE));
        setPiece(new Square(1, 4), new Pawn(Color.WHITE));
        setPiece(new Square(1, 5), new Pawn(Color.WHITE));
        setPiece(new Square(1, 6), new Pawn(Color.WHITE));
        setPiece(new Square(1, 7), new Pawn(Color.WHITE));
        //Initialize black pieces
        setPiece(new Square(6, 0), new Pawn(Color.BLACK));
        setPiece(new Square(6, 1), new Pawn(Color.BLACK));
        setPiece(new Square(6, 2), new Pawn(Color.BLACK));
        setPiece(new Square(6, 3), new Pawn(Color.BLACK));
        setPiece(new Square(6, 4), new Pawn(Color.BLACK));
        setPiece(new Square(6, 5), new Pawn(Color.BLACK));
        setPiece(new Square(6, 6), new Pawn(Color.BLACK));
        setPiece(new Square(6, 7), new Pawn(Color.BLACK));
        setPiece(new Square(7, 0), new Rook(Color.BLACK));
        setPiece(new Square(7, 1), new Knight(Color.BLACK));
        setPiece(new Square(7, 2), new Bishop(Color.BLACK));
        setPiece(new Square(7, 3), new Queen(Color.BLACK));
        setPiece(new Square(7, 4), new King(Color.BLACK));
        setPiece(new Square(7, 5), new Bishop(Color.BLACK));
        setPiece(new Square(7, 6), new Knight(Color.BLACK));
        setPiece(new Square(7, 7), new Rook(Color.BLACK));
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
