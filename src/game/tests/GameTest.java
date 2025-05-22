package game.tests;

import java.util.Collection;
import game.*;

public class GameTest extends Test {
    public static void main(String[] args) {
        boolean caught = false;
        try { new GameImpl(0); } catch (IllegalArgumentException e) { caught = true; }
        expect(true, caught);

        caught = false;
        try { new GameImpl(5); } catch (IllegalArgumentException e) { caught = true; }
        expect(false, caught);
        
        Game g = new GameImpl(3);
        expect(PieceColour.WHITE, g.currentPlayer());
        g.makeMove(new MoveImpl(0,0));
        expect(PieceColour.BLACK, g.currentPlayer());
        g.makeMove(new MoveImpl(0,1));
        expect(PieceColour.WHITE, g.currentPlayer());

        
        caught = false;
        try { g.makeMove(new MoveImpl(0,1)); }                  
        catch (IllegalArgumentException e) { caught = true; }
        expect(true, caught);

        Game h = new GameImpl(3);
        h.makeMove(new MoveImpl(0,0)); 
        h.makeMove(new MoveImpl(1,0));
        h.makeMove(new MoveImpl(0,1)); 
        h.makeMove(new MoveImpl(1,1));
        h.makeMove(new MoveImpl(0,2));
        expect(true, h.isOver());
        expect(PieceColour.WHITE, h.winner());

        
        Game v = new GameImpl(3);
        v.makeMove(new MoveImpl(0,0));
        v.makeMove(new MoveImpl(0,1));
        v.makeMove(new MoveImpl(1,0));
        v.makeMove(new MoveImpl(1,1));
        v.makeMove(new MoveImpl(2,2));
        v.makeMove(new MoveImpl(2,1));
        expect(true, v.isOver());
        expect(PieceColour.BLACK, v.winner());

        
        Game d = new GameImpl(2);
        d.makeMove(new MoveImpl(0,0));
        d.makeMove(new MoveImpl(0,1));
        d.makeMove(new MoveImpl(1,0));
        d.makeMove(new MoveImpl(1,1));
        expect(true, d.isOver());
        expect(PieceColour.NONE, d.winner());

        
        Game c1 = new GameImpl(3);
        c1.makeMove(new MoveImpl(0,0));
        Game c2 = c1.copy();
        c2.makeMove(new MoveImpl(0,1));
        
        expect(PieceColour.NONE, c1.getGrid().getPiece(0,1));

        checkAllTestsPassed();
    }
}
