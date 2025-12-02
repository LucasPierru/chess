import java.util.List;

public abstract class Piece {
    private String name;
    protected Color color;

    public Piece(Color color){
        this.color = color;
    }

    abstract public void move();

    public abstract List<Move> calculateLegalMoves(Board board, Square from);


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
