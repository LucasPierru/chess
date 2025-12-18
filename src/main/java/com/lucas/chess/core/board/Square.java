package com.lucas.chess.core.board;

import com.lucas.chess.core.move.IllegalMoveException;

import java.util.Objects;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class Square {
    private int col;
    private int row;
    private final char[] LETTERS = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};

    public Square(int row, int col){
        this.row = row;
        this.col = col;
    }

    public Square(String notation) throws IllegalMoveException {
        if (notation.length() > 2) {
            throw new IllegalMoveException("Square doesn't exist");
        }
        OptionalInt col = translateNotationToCol(notation);
        int row = translateNotationToRow(notation);

        if (col.isPresent()) {
            this.row = row;
            this.col = col.getAsInt();
        } else {
            throw new IllegalMoveException("Square doesn't exist");
        }
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return col == square.col && row == square.row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(col, row);
    }

    public String translateSquareToNotation() {
        return "" + this.LETTERS[col] + (row + 1);
    }

    private OptionalInt translateNotationToCol (String notation) {
        return IntStream.range(0, LETTERS.length)
                .filter(i -> LETTERS[i] == notation.charAt(0))
                .findFirst();
    }

    private int translateNotationToRow (String notation) {
        return (int)notation.charAt(1) - 1 - '0';
    }
}
