package game;

/**
 * Implementasi antarmuka Move.
 */
public class MoveImpl implements Move {
    private final int row;
    private final int col;

    public MoveImpl(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof MoveImpl)) return false;
        MoveImpl other = (MoveImpl) obj;
        return row == other.row && col == other.col;
    }

    @Override
    public int hashCode() {
        return 31 * row + col;
    }

    @Override
    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        return "(" + row + "," + col + ")";
    }
}
