package com.lucas.chess.move;

public class IllegalMoveException extends Exception{
    public IllegalMoveException(String message) {
        super(message);
    }
}
