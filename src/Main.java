import board.Board;
import game.Game;
import move.MoveGenerator;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        MoveGenerator moveGenerator = new MoveGenerator(board);
        Game game = new Game(moveGenerator);
        game.start();
    }
}