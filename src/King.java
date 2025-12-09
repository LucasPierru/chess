public class King extends Piece{
    private boolean hasCastleRights;

    public King(Color color) {
        super(color);
        this.setName("K");
        this.hasCastleRights = true;
    }

    public boolean getHasCastleRights() {
        return hasCastleRights;
    }

    public void setHasCastleRights(boolean hasCastleRights) {
        this.hasCastleRights = hasCastleRights;
    }
}
