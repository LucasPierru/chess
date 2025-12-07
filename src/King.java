public class King extends Piece{
    public King(Color color) {
        super(color);
        this.setName("K");
    }

    private boolean isKingInCheck(BoardView board, Square to) {
        Square kingPosition = board.getKingPosition(this.getColor());
        return board.isSquareAttacked(kingPosition, this.getColor());
    }


}
