import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        board.print();
        System.out.println(board.getSideToMove() + " to move");
        Square from = board.getSquare(1, 4);
        Piece piece = from.getPiece();
        List<Move> legalMoves = piece.calculateLegalMoves(board, from);
        System.out.println(legalMoves + " " + piece.getColor() + "-" + piece.getName() + " has " + legalMoves.size() + " Moves available");
        try {
            board.movePiece(from, board.getSquare(3,4));
        } catch (IllegalMoveException e) {
            System.out.println("⚠️ " + e.getMessage());
        }
        board.print();
        System.out.println(board.getSideToMove() + " to move");
    }
}