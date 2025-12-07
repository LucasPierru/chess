public interface BoardView {
    boolean isValidSquare(int row, int col);
    Square getSquare(int row, int col);
    boolean isSquareAttacked(Square square, Color color);
    Square getKingPosition(Color kingColor);
}
