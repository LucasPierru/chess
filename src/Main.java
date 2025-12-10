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
            moveGenerator.movePiece(new Square("e2"), new Square("e4" ));
            board.print();
            moveGenerator.movePiece(new Square("e7"), new Square("e5" ));
            board.print();
            moveGenerator.movePiece(new Square("f2"), new Square("f4"));
            board.print();
            moveGenerator.movePiece(new Square("b8"), new Square("c6"));
            board.print();
            moveGenerator.movePiece(new Square("g1"), new Square("f3"));
            board.print();
        } catch (IllegalMoveException e) {
            System.out.println("⚠️ " + e.getMessage());
        } catch (CloneNotSupportedException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }

        for (Move move: moveGenerator.getMoveHistory()){
            if (moveGenerator.getMoveHistory().indexOf(move) % 2 == 0) {
                System.out.print(move.translateMoveToNotation() + " ");
            } else {
                System.out.println(move.translateMoveToNotation() + ";");
            }
        }
        System.out.println("");
        System.out.println(board.getSideToMove() + " to move");
    }
}