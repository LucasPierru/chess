package com.lucas.chess.dto;

import com.lucas.chess.core.game.GameResult;
import com.lucas.chess.core.piece.Color;

public record BoardUpdateMessage(String fen, MessageType messageType, GameResult result, Color winnerColor ) {
}
