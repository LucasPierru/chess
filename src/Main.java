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
            board.print();
            moveGenerator.movePiece(new Square(6, 4), new Square(4,4 ));
            board.print();
            moveGenerator.movePiece(new Square(1, 5), new Square(3,5 ));
            board.print();
            moveGenerator.movePiece(new Square(7, 3), new Square(3, 7));
            board.print();
            moveGenerator.movePiece(new Square(0, 1), new Square(2, 3));
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

        System.out.println(board.getSideToMove() + " to move");
    }
}