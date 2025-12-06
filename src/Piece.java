import java.util.List;

public abstract class Piece {
    private String name;
    protected Color color;

    public Piece(Color color){
        this.color = color;
    }

    public void move(Board board, Square from, Square to) throws IllegalMoveException {
        Piece piece = board.getPiece(from);

        if (piece == null) {
            throw new IllegalMoveException("No piece at " + from);
        }

        List<Move> legalMoves = this.calculateLegalMoves(board, from);

        for (Move move: legalMoves) {
            if (move.to().equals(to)) {
                board.setPiece(to, piece);
                board.setPiece(from, null);
                board.switchSide();
                return;
            }
        }

        throw new IllegalMoveException("Illegal move: " + piece.getClass().getSimpleName()
                + " cannot move from " + from + " to " + to);
    }

    public abstract List<Move> calculateLegalMoves(Board board, Square from);

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
}
