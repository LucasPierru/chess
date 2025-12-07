import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        MoveGenerator moveGenerator = new MoveGenerator(board);
        board.print();
        System.out.println(board.getSideToMove() + " to move");
        Square from = new Square(1, 4);
        Square to = new Square(3, 4);
        Piece piece = board.getPiece(1, 4);
        List<Move> legalMoves = moveGenerator.legalMoves(from);
        System.out.println(legalMoves + " " + piece.getColor() + "-" + piece.getName() + " has " + legalMoves.size() + " Moves available");
        try {
            moveGenerator.movePiece(from, to);
        } catch (IllegalMoveException e) {
            System.out.println("⚠️ " + e.getMessage());
        }
        board.print();
        System.out.println(board.getSideToMove() + " to move");
    }
}