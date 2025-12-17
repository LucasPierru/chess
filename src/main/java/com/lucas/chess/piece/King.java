package com.lucas.chess.piece;

public class King extends Piece {
    private boolean hasShortCastleRights;
    private boolean hasLongCastleRights;

    public King(Color color) {
        super(color);
        this.setName("K");
        this.hasShortCastleRights = true;
        this.hasLongCastleRights = true;
    }

    public boolean getHasShortCastleRights() {
        return hasShortCastleRights;
    }

    public void setHasShortCastleRights(boolean hasCastleRights) {
        this.hasShortCastleRights = hasCastleRights;
    }

    public boolean getHasLongCastleRights() {
        return hasLongCastleRights;
    }

    public void setHasLongCastleRights(boolean hasLongCastleRights) {
        this.hasLongCastleRights = hasLongCastleRights;
    }
}
