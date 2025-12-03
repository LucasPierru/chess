import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        board.print();
        Square square = new Square(6,7);
        Piece piece = board.getPiece(square);
        List<Move> legalMoves = piece.calculateLegalMoves(board, square);
        System.out.println(legalMoves + " " + piece.getName() + " " + legalMoves.size() + " Moves available");
    }
}