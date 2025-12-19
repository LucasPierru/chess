package com.lucas.chess.dto;

public record BoardUpdateMessage(String fen, MessageType messageType) {
}
