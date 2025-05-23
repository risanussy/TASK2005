/**
 * Implementation of the Game interface for a turn-based grid game (e.g., Hex-like).
 * Manages the game state, current player, and win condition detection.
 */
package game;

import java.util.ArrayList;
import java.util.Collection;

public class GameImpl implements Game {
    /**
     * Underlying grid holding pieces for each cell.
     */
    private final GridImpl grid;

    /**
     * The player whose turn it currently is.
     */
    private PieceColour currentPlayer;

    /**
     * Flag indicating whether the game has finished.
     */
    private boolean over;

    /**
     * Winner of the game (NONE if draw or not yet decided).
     */
    private PieceColour winner;

    /**
     * Constructs a new game with an empty grid of the given size.
     * @param size Number of rows/columns for the square grid; must be positive.
     * @throws IllegalArgumentException if size is not positive.
     */
    public GameImpl(int size) {
        if (size <= 0) throw new IllegalArgumentException("Size must be positive");
        this.grid = new GridImpl(size);
        this.currentPlayer = PieceColour.WHITE;
        this.over = false;
        this.winner = PieceColour.NONE;
    }

    /**
     * Checks if the game has ended, updating the state first.
     * @return true if the game is over, false otherwise.
     */
    @Override
    public boolean isOver() {
        updateState();
        return over;
    }

    /**
     * Returns the winner of the game, updating the state first.
     * @return the PieceColour of the winner, or NONE if draw or ongoing.
     */
    @Override
    public PieceColour winner() {
        updateState();
        return winner;
    }

    /**
     * Returns which player's turn it is.
     * @return the current player's PieceColour.
     */
    @Override
    public PieceColour currentPlayer() {
        return currentPlayer;
    }

    /**
     * Lists all legal moves available on the current board.
     * @return a collection of Move objects for each empty cell.
     */
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

    /**
     * Executes a move for the current player, then toggles turn if game not over.
     * @param move the Move to apply; must not be null or out of bounds,
     *             and the target cell must be empty.
     * @throws IllegalArgumentException on invalid move.
     */
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

    /**
     * Returns a copy of the current grid state.
     * @return a deep copy of the Grid.
     */
    @Override
    public Grid getGrid() {
        return grid.copy();
    }

    /**
     * Creates a full copy of this GameImpl, including grid, turn, and state.
     * @return a new GameImpl instance mirroring the current game state.
     */
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

    /**
     * Internal helper to detect win conditions or draw and update flags.
     */
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
