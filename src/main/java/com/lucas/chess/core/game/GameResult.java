package com.lucas.chess.core.game;

public enum GameResult {
    RESIGNATION,
    CHECKMATE,
    TIMEOUT,
    STALEMATE,
    DRAW_AGREEMENT,
    DRAW_INSUFFICIENT_MATERIAL,
    DRAW_FIFTY_MOVE_RULE,
    DRAW_THREEFOLD_REPETITION
}
