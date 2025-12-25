package com.lucas.chess.wasm;

import com.lucas.chess.core.board.Board;
import com.lucas.chess.core.board.Square;
import com.lucas.chess.core.move.Move;
import com.lucas.chess.core.move.MoveGenerator;
import com.lucas.chess.core.piece.Color;
import org.teavm.jso.JSExport;
import org.teavm.jso.browser.Window;

import java.util.List;

public class MainClass {
    private static Board board = new Board();
    private static MoveGenerator moveGenerator = new MoveGenerator(board);

    public static void main(String[] args) {
        Window.current().alert("Hello from Java (via TeaVM)!");
    }

    @JSExport
    public static String greet(String name) {
        return "Hello, " + name + "!";
    }

    @JSExport
    public static String getFEN() {
        return moveGenerator.convertToFEN(Color.WHITE);
    }

    @JSExport
    public static boolean playMove(String from, String to) {
        try {
            return moveGenerator.movePiece(
                    new Square(from),
                    new Square(to),
                    null,
                    Color.WHITE
            );
        } catch (Exception e) {
            return false;
        }
    }

    @JSExport
    public static String[] getLegalMoves(String square) {
        try {
            List<Move> moves = moveGenerator.legalMoves(
                    new Square(square),
                    Color.WHITE
            );

            String[] result = new String[moves.size()];
            for (int i = 0; i < moves.size(); i++) {
                result[i] = moves.get(i).translateMoveToNotation();
            }
            return result;

        } catch (Exception e) {
            return new String[0];
        }
    }
}
