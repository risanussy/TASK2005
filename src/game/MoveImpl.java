/**
 * Concrete implementation of the Move interface representing a single move on the board.
 * Encapsulates row and column indices for a move.
 */
package game;

public class MoveImpl implements Move {
    /**
     * Zero-based row index for this move.
     */
    private final int row;

    /**
     * Zero-based column index for this move.
     */
    private final int col;

    /**
     * Constructs a move at the specified row and column.
     * @param row zero-based row index.
     * @param col zero-based column index.
     */
    public MoveImpl(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Retrieves the row index of this move.
     * @return the row coordinate.
     */
    @Override
    public int getRow() {
        return row;
    }

    /**
     * Retrieves the column index of this move.
     * @return the column coordinate.
     */
    @Override
    public int getCol() {
        return col;
    }

    /**
     * Equality check based on row and column values.
     * @param obj the object to compare.
     * @return true if obj is a MoveImpl with same row and col, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof MoveImpl)) return false;
        MoveImpl other = (MoveImpl) obj;
        return row == other.row && col == other.col;
    }

    /**
     * Generates a hash code consistent with equals().
     * @return hash code computed from row and column.
     */
    @Override
    public int hashCode() {
        return 31 * row + col;
    }

    /**
     * Returns a string representation of the move in the format (row,col).
     * @return string form of this move.
     */
    @Override
    public String toString() {
        return "(" + row + "," + col + ")";
    }
}
