/**
 * Implementation of the Grid interface representing a square board of pieces.
 * Supports accessing, modifying, and copying the board state.
 */
package game;

import game.PieceColour;

public class GridImpl implements Grid {
    /**
     * Underlying 2D array storing the color of each piece on the board.
     */
    private final PieceColour[][] board;

    /**
     * Dimension of the square grid (number of rows and columns).
     */
    private final int size;

    /**
     * Constructs an empty grid of the specified size, initializing all cells to NONE.
     * @param size the number of rows and columns; must be positive.
     * @throws IllegalArgumentException if size is not positive.
     */
    public GridImpl(int size) {
        if (size <= 0) throw new IllegalArgumentException("Size must be positive");
        this.size = size;
        board = new PieceColour[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = PieceColour.NONE;
            }
        }
    }

    /**
     * Returns the dimension of the grid (size x size).
     * @return the grid size.
     */
    @Override
    public int getSize() {
        return size;
    }

    /**
     * Retrieves the piece at the given coordinates.
     * @param row zero-based row index.
     * @param col zero-based column index.
     * @return the PieceColour at the specified cell.
     * @throws IllegalArgumentException if row or col is out of bounds.
     */
    @Override
    public PieceColour getPiece(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size)
            throw new IllegalArgumentException("Position out of bounds");
        return board[row][col];
    }

    /**
     * Places a piece on the board at the specified coordinates.
     * @param row zero-based row index.
     * @param col zero-based column index.
     * @param piece the PieceColour to set; must be WHITE or BLACK.
     * @throws IllegalArgumentException if position is out of bounds or piece is null/NONE.
     */
    @Override
    public void setPiece(int row, int col, PieceColour piece) {
        if (row < 0 || row >= size || col < 0 || col >= size)
            throw new IllegalArgumentException("Position out of bounds");
        if (piece == null || piece == PieceColour.NONE)
            throw new IllegalArgumentException("Invalid piece colour");
        if (board[row][col] != PieceColour.NONE)
            throw new IllegalArgumentException("Position already occupied");
        board[row][col] = piece;
    }
    /**
     * Creates a deep copy of this grid, including all piece placements.
     * @return a new GridImpl instance with the same size and cell values.
     */
    @Override
    public Grid copy() {
        GridImpl copy = new GridImpl(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                copy.board[i][j] = this.board[i][j];
            }
        }
        return copy;
    }

    /**
     * Returns a string representation of the board for debugging:
     * '.' for empty, 'W' for white, 'B' for black.
     * Each row is on a new line.
     * @return multi-line board state string.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                switch (board[i][j]) {
                    case NONE:  sb.append('.'); break;
                    case WHITE: sb.append('W'); break;
                    case BLACK: sb.append('B'); break;
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
