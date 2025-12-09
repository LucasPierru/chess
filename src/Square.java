import java.util.Objects;

public class Square {
    private int col;
    private int row;
    private final String[] LETTERS = {"a", "b", "c", "d", "e", "f", "g", "h"};

    public Square(int row, int col){
        this.row = row;
        this.col = col;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
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

    public String translateMoveToNotation() {
        return this.LETTERS[col] + (row + 1);
    }
}
