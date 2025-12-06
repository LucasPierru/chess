//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        Square square = new Square(1,4);
        board.print();
        System.out.println(board.getSideToMove() + " to move");
        Piece piece = board.getPiece(square);
        /*List<Move> legalMoves = piece.calculateLegalMoves(board, square);
        System.out.println(legalMoves + " " + piece.getColor() + "-" + piece.getName() + " has " + legalMoves.size() + " Moves available");*/
        try {
            piece.move(board, square, new Square(3, 4));
        } catch (IllegalMoveException e) {
            System.out.println("⚠️ " + e.getMessage());
        }
        board.print();
        System.out.println(board.getSideToMove() + " to move");
    }
}