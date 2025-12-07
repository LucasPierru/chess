public abstract class Piece {
    private String name;
    private int value;
    protected Color color;
    private final String[] LETTERS = {"a", "b", "c", "d", "e", "f", "g", "h"};

    public Piece(Color color){
        this.color = color;
    }

    /*public boolean isCheckingKing(Board board, Square from) {
        Square kingSquare = board.getKingPosition(color == Color.WHITE ? Color.BLACK : Color.WHITE);
        List<Move> legalMoves = this.calculateLegalMoves(board, from);

        for (Move move: legalMoves) {
            if (move.to().equals(kingSquare)) {
                return true;
            }
        }

        return false;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
