package game.tests;

import java.util.Collection;
import game.*;

public class GameTest extends Test {
    public static void main(String[] args) {
        /* ---------- konstruktor ---------- */
        boolean caught = false;
        try { new GameImpl(0); } catch (IllegalArgumentException e) { caught = true; }
        expect(true, caught);

        /* ---------- game normal ---------- */
        Game g = new GameImpl(3);
        expect(PieceColour.WHITE, g.currentPlayer());

        g.makeMove(new MoveImpl(0,0));          // W
        expect(PieceColour.BLACK, g.currentPlayer());
        g.makeMove(new MoveImpl(1,0));          // B
        expect(PieceColour.WHITE, g.currentPlayer());

        /* ---------- invalid moves ---------- */
        caught = false;
        try { g.makeMove(null); }               // null move
        catch (IllegalArgumentException e) { caught = true; }
        expect(true, caught);

        caught = false;
        try { g.makeMove(new MoveImpl(5,5)); } // out of bounds
        catch (IllegalArgumentException e) { caught = true; }
        expect(true, caught);

        caught = false;
        try { g.makeMove(new MoveImpl(0,0)); } // duplicate
        catch (IllegalArgumentException e) { caught = true; }
        expect(true, caught);

        /* ---------- getMoves() & grid state ---------- */
        Collection<Move> moves = g.getMoves();
        expect(7, moves.size());                // 9 sel – 2 terisi

        // (0,1) harus masih kosong
        expect(PieceColour.NONE, g.getGrid().getPiece(0,1));

        /* ---------- kemenangan horizontal (putih) ---------- */
        Game h = new GameImpl(3);
        h.makeMove(new MoveImpl(0,0)); // W
        h.makeMove(new MoveImpl(1,0)); // B
        h.makeMove(new MoveImpl(0,1)); // W
        h.makeMove(new MoveImpl(1,1)); // B
        h.makeMove(new MoveImpl(0,2)); // W -> menang
        expect(true, h.isOver());
        expect(PieceColour.WHITE, h.winner());

        /* ---------- kemenangan vertikal (hitam) ---------- */
        Game v = new GameImpl(3);
        v.makeMove(new MoveImpl(0,0)); // W
        v.makeMove(new MoveImpl(0,1)); // B
        v.makeMove(new MoveImpl(2,2)); // W
        v.makeMove(new MoveImpl(1,1)); // B
        v.makeMove(new MoveImpl(1,0)); // W
        v.makeMove(new MoveImpl(2,1)); // B -> menang
        expect(true, v.isOver());
        expect(PieceColour.BLACK, v.winner());

        /* ---------- simultaneous win  →  draw ---------- */
        Game s = new GameImpl(2);
        // urutan langkah membuat kedua pemain punya jalur setelah langkah terakhir
        s.makeMove(new MoveImpl(0,0)); // W
        s.makeMove(new MoveImpl(0,1)); // B
        s.makeMove(new MoveImpl(1,1)); // W  (sekarang W vertikal & B horizontal)
        expect(true, s.isOver());
        expect(PieceColour.NONE, s.winner());

        /* ---------- draw (papan penuh) ---------- */
        Game d = new GameImpl(2);
        d.makeMove(new MoveImpl(0,0)); // W
        d.makeMove(new MoveImpl(0,1)); // B
        d.makeMove(new MoveImpl(1,1)); // W
        d.makeMove(new MoveImpl(1,0)); // B – penuh
        expect(true, d.isOver());
        expect(PieceColour.NONE, d.winner());

        /* ---------- copy() independen ---------- */
        Game original = new GameImpl(3);
        original.makeMove(new MoveImpl(0,0));     // W
        Game clone   = original.copy();
        clone.makeMove(new MoveImpl(0,1));        // B pada salinan
        // pastikan grid original tidak terpengaruh
        expect(PieceColour.NONE, original.getGrid().getPiece(0,1));
        // pastikan state original (over / winner) tetap sama
        expect(false, original.isOver());
        expect(PieceColour.NONE, original.winner());

        checkAllTestsPassed();
    }
}
