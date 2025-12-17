package com.lucas.chess.game;

import com.lucas.chess.board.Square;
import com.lucas.chess.move.IllegalMoveException;
import com.lucas.chess.move.MoveGenerator;
import com.lucas.chess.piece.Color;

import java.util.Scanner;

public class Game {
    private final MoveGenerator moveGenerator;
    private Color sideToMove = Color.WHITE;

    public Game(MoveGenerator moveGenerator) {
        this.moveGenerator = moveGenerator;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while(!isCheckmate() && !isStalemate()) {
            try {
                System.out.println(sideToMove + " to main.java.chess.move.");
                System.out.println("Move from what square:");
                String squareFrom = scanner.nextLine();
                System.out.println("To what square:");
                String squareTo = scanner.nextLine();

                System.out.print("\033[H\033[2J");
                Square from = new Square(squareFrom);
                Square to = new Square(squareTo);
                moveGenerator.movePiece(from, to, null, this.sideToMove);
                switchSide();
                moveGenerator.printBoard();
                moveGenerator.printHistory();
            } catch (IllegalMoveException | CloneNotSupportedException e) {
                System.out.println(e);
            }
        }
        scanner.close();
        if (isCheckmate()) {
            System.out.println("Checkmate ! " + getWinner() + " wins!");
        }

        if (isStalemate()) {
            System.out.println("Stalemate ! it's a draw !");
        }
    }

    private boolean isCheckmate() {
        boolean isKingInCheck = moveGenerator.getBoard().isKingInCheck(sideToMove);
        boolean hasLegalMoves = moveGenerator.hasLegalMoves(sideToMove);

        return isKingInCheck && !hasLegalMoves;
    }

    private boolean isStalemate() {
        boolean isKingInCheck = moveGenerator.getBoard().isKingInCheck(sideToMove);
        boolean hasLegalMoves = moveGenerator.hasLegalMoves(sideToMove);

        return !isKingInCheck && !hasLegalMoves;
    }

    private Color getWinner() {
        return sideToMove == Color.WHITE ? Color.BLACK : Color.WHITE;
    }

    private void switchSide() {
        this.sideToMove = this.sideToMove == Color.WHITE ? Color.BLACK : Color.WHITE;
    }
}
