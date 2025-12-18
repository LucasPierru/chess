package com.lucas.chess.core.move;

public class IllegalMoveException extends Exception{
    public IllegalMoveException(String message) {
        super(message);
    }
}
