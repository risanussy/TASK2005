package game;

import game.PieceColour;

public class GridImpl implements Grid {
    private final PieceColour[][] board;
    private final int size;

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

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public PieceColour getPiece(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size)
            throw new IllegalArgumentException("Position out of bounds");
        return board[row][col];
    }

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
