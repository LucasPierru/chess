package piece;

public class Rook extends Piece {
    private int[][] DIRECTIONS = { {0, -1}, {0, 1}, {-1, 0}, {1, 0} };
    public Rook(Color color) {
        super(color);
        this.setName("R");
    }


    public int[][] getDirections() {
        return this.DIRECTIONS;
    }
}
