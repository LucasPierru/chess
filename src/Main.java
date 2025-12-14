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
        Piece piece = board.getPiece(1, 4);
        /*List<Move> legalMoves = moveGenerator.legalMoves(from);
        System.out.println(legalMoves + " " + piece.getColor() + "-" + piece.getName() + " has " + legalMoves.size() + " Moves available");*/
        try {
            moveGenerator.movePiece(new Square("e2"), new Square("e4" ));
            board.print();
            moveGenerator.movePiece(new Square("d7"), new Square("d5"));
            board.print();
            moveGenerator.movePiece(new Square("e4"), new Square("e5" ));
            board.print();
            moveGenerator.movePiece(new Square("f7"), new Square("f5" ));
            board.print();
            moveGenerator.movePiece(new Square("e5"), new Square("f6" ));
            board.print();
            /*moveGenerator.movePiece(new Square("e7"), new Square("e5" ));
            board.print();
            moveGenerator.movePiece(new Square("f2"), new Square("f4"));
            board.print();
            moveGenerator.movePiece(new Square("b8"), new Square("c6"));
            board.print();
            moveGenerator.movePiece(new Square("g1"), new Square("f3"));
            board.print();
            moveGenerator.movePiece(new Square("g8"), new Square("f6"));
            board.print();
            moveGenerator.movePiece(new Square("f1"), new Square("b5"));
            board.print();
            moveGenerator.movePiece(new Square("a7"), new Square("a6"));
            board.print();
            moveGenerator.movePiece(new Square("e1"), new Square("g1"));
            board.print();*/
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
        try {
            Square pawnSquare = new Square("e5");
            List<Move> legalMoves = moveGenerator.legalMoves(pawnSquare);
            for (Move move: legalMoves){
                System.out.println(move.getPiece().getName() + " can move from " + move.getFrom().translateSquareToNotation() + " to " + move.getTo().translateSquareToNotation() + " " + move.getMoveType());
            }
        } catch (IllegalMoveException e) {
            System.out.println("⚠️ " + e.getMessage());
        }


        System.out.println("");
        System.out.println(board.getSideToMove() + " to move");
    }
}