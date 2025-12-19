package com.lucas.chess.core.board;

import com.lucas.chess.core.piece.*;

import java.util.ArrayList;
import java.util.List;

public class Board implements Cloneable  {
    private final Piece[][] board = new Piece[8][8];
    private final String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h"};

    public Board() {
        this.initializeBoard();
    }

    public Piece getPiece(int row, int col) {
        return isValidSquare(row, col) ? this.board[row][col] : null;
    }

    public void setPiece(int row, int col, Piece piece) {
        this.board[row][col] = piece;
    }

    public boolean isValidSquare(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    public Square getKingPosition(Color pieceColor) {
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

    public List<Square> getSideSquares(Color pieceColor) {
        List<Square> squares = new ArrayList<>();
        Piece[][] pieces = getBoard();
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++){
                Piece piece = pieces[i][j];
                if (piece != null  && piece.getColor() == pieceColor) {
                    squares.add(new Square(i, j));
                }
            }
        }

        return squares;
    }

    public Piece[][] getBoard() {
        return board;
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

    public boolean isKingInCheck(Color color) {
        Square kingPosition = this.getKingPosition(color);
        return this.isSquareAttacked(kingPosition, color);
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

        // initialize remaining squares without any main.java.chess.piece
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
                    String ANSI_COLOR = this.board[i][j].getColor() == Color.BLACK ? "\u001B[34m" : "\u001B[0m";
                    String ANSI_RESET = "\u001B[0m";
                    System.out.print(ANSI_COLOR + this.board[i][j].getColor().toString().charAt(0) + "-" + this.board[i][j].getName() + " " + ANSI_RESET);
                }
            }
            System.out.println();
        }
        for (String letter : this.letters) {
            System.out.print("  ");
            System.out.print(" " + letter);
        }
        System.out.println();
    }



    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Board deepCopy() {
        Board copy = new Board();

        // deep copy the 2D main.java.chess.piece array
        for (int r = 0; r < 8; r++) {
            // clone the row array so rows don't share references
            copy.board[r] = new Piece[8];
            for (int f = 0; f < 8; f++) {
                Piece p = this.board[r][f];
                if (p == null) {
                    copy.board[r][f] = null;
                } else {
                    // If Piece is immutable you can reuse the reference:
                    copy.board[r][f] = p;

                    // If Piece is mutable, replace the previous line with something like:
                    // copy.main.java.chess.board[r][f] = p.clone(); // or new main.java.chess.piece.Piece(p) if you have a copy constructor
                }
            }
        }

        return copy;
    }
}
