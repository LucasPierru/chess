public interface BoardView {
    boolean isValidSquare(int row, int col);
    Piece getPiece(int row, int col);
    boolean isSquareAttacked(Square square, Color color);
    Square getKingPosition(Color kingColor);
}
