package game;

import java.util.ArrayList;
import java.util.Collection;


public class GameImpl implements Game {
    private final GridImpl grid;
    private PieceColour currentPlayer;
    private boolean over;
    private PieceColour winner;

    public GameImpl(int size) {
        if (size <= 0) throw new IllegalArgumentException("Size must be positive");
        this.grid = new GridImpl(size);
        this.currentPlayer = PieceColour.WHITE;
        this.over = false;
        this.winner = PieceColour.NONE;
    }

    @Override
    public boolean isOver() {
        updateState();
        return over;
    }

    @Override
    public PieceColour winner() {
        updateState();
        return winner;
    }

    @Override
    public PieceColour currentPlayer() {
        return currentPlayer;
    }

    @Override
    public Collection<Move> getMoves() {
        Collection<Move> moves = new ArrayList<>();
        int n = grid.getSize();
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (grid.getPiece(r, c) == PieceColour.NONE)
                    moves.add(new MoveImpl(r, c));
            }
        }
        return moves;
    }

    @Override
    public void makeMove(Move move) {
        if (move == null) throw new IllegalArgumentException("Move is null");
        int r = move.getRow(), c = move.getCol();
        if (r < 0 || r >= grid.getSize() || c < 0 || c >= grid.getSize())
            throw new IllegalArgumentException("Move out of bounds");
        if (grid.getPiece(r, c) != PieceColour.NONE)
            throw new IllegalArgumentException("Position already occupied");

        grid.setPiece(r, c, currentPlayer);
        updateState();
        if (!over) {
            currentPlayer = (currentPlayer == PieceColour.WHITE) ? PieceColour.BLACK : PieceColour.WHITE;
        }
    }

    @Override
    public Grid getGrid() {
        return grid.copy();
    }

    @Override
    public Game copy() {
        GameImpl copy = new GameImpl(grid.getSize());
        for (int r = 0; r < grid.getSize(); r++) {
            for (int c = 0; c < grid.getSize(); c++) {
                PieceColour p = grid.getPiece(r, c);
                if (p != PieceColour.NONE)         
                    copy.grid.setPiece(r, c, p);
            }
        }
        copy.currentPlayer = this.currentPlayer;
        copy.over          = this.over;
        copy.winner        = this.winner;
        return copy;
    }

    
    private void updateState() {
        if (over) return;

        boolean whiteWin = PathFinder.leftToRight(grid, PieceColour.WHITE) ||
                        PathFinder.topToBottom(grid, PieceColour.WHITE);
        boolean blackWin = PathFinder.leftToRight(grid, PieceColour.BLACK) ||
                        PathFinder.topToBottom(grid, PieceColour.BLACK);

        if (whiteWin && blackWin) {
            over   = true;
            winner = PieceColour.NONE; 
            return;
        }
        if (whiteWin) {
            over   = true;
            winner = PieceColour.WHITE;
            return;
        }
        if (blackWin) {
            over   = true;
            winner = PieceColour.BLACK;
            return;
        }
        if (getMoves().isEmpty()) {        
            over   = true;
            winner = PieceColour.NONE;
        }
    }

}
