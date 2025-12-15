import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        MoveGenerator moveGenerator = new MoveGenerator(board);
        MoveDto[] moves = {
                new MoveDto("e2", "e4", null),
                new MoveDto("d7", "d5", null),
                new MoveDto("e4", "e5", null),
                new MoveDto("f7", "f5", null),
                new MoveDto("e5", "f6", null),
                new MoveDto("e7", "e6", null),
                new MoveDto("f6", "f7", null),
                new MoveDto("e8", "d7", null),
                new MoveDto("a2", "a3", null),
                new MoveDto("f8", "e7", null),
                new MoveDto("f7", "g8", PieceType.KNIGHT),
        };
        try {
            for (MoveDto move: moves) {
                Square from = new Square(move.from());
                Square to = new Square(move.to());
                moveGenerator.movePiece(from, to, move.promotion());
            }
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
        try {
            Square pawnSquare = new Square("g8");
            List<Move> legalMoves = moveGenerator.legalMoves(pawnSquare);
            if (legalMoves.isEmpty()) {
                System.out.println("No moves available for that piece");
            }
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