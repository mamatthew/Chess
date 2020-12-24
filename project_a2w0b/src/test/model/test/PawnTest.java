package model.test;

import model.Board;
import model.Color;
import model.pieces.Pawn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PawnTest {
    Board board;
    Pawn pawn1;
    Pawn pawn2;

    @BeforeEach
    public void runBefore() {
        board = new Board();
        pawn1 = new Pawn(Color.WHITE, board);
        pawn2 = new Pawn(Color.BLACK, board);
        board.addPiece(pawn1, board.getSquares()[3][1]);
        board.addPiece(pawn2, board.getSquares()[3][6]);
    }
    @Test
    public void testConstructor() {
        Assertions.assertEquals(Color.WHITE, pawn1.getColor());
        Assertions.assertEquals(board, pawn1.getBoard());
        Assertions.assertTrue(!pawn1.getHasMoved());
    }

    @Test
    public void testFindPathWhite() {
        Assertions.assertTrue(pawn1.findPath(board.getSquares()[3][2]));
    }
    @Test
    public void testFindPathBlack() {
        Assertions.assertTrue(pawn2.findPath(board.getSquares()[3][5]));
    }

    @Test
    public void testFindValidWhitePathBackwards() {
        Assertions.assertFalse(pawn1.findValidWhitePath(3, 1, 3, 5));
    }

    @Test
    public void testFindValidBlackPathBackwards() {
        Assertions.assertFalse(pawn2.findValidWhitePath(3, 6, 3, 3));

    }

    @Test
    public void testGetValue() {
        assertEquals(1, pawn1.getValue());
    }

    @Test
    public void testFindValidWhitePathAlreadyMovedFail() {
        pawn1.setHasMoved(true);
        Assertions.assertFalse(pawn1.findValidWhitePath(3, 1, 3, 3));
    }



    @Test
    public void testFindValidBlackPathDoubleFail() {
        Assertions.assertTrue(pawn2.findValidBlackPath(3, 6, 3, 4));
    }

    @Test
    public void testFindValidBlackPathDoublePass() {
        pawn2.setHasMoved(true);
        Assertions.assertFalse(pawn2.findValidBlackPath(3, 6, 3, 4));
    }

    @Test
    public void testFindValidBlackPathBackWardds() {
        pawn2.setHasMoved(true);
        Assertions.assertFalse(pawn2.findValidBlackPath(3, 6, 3, 7));
    }

    @Test
    public void testFindValidWhitePathDoubleFail() {
        pawn1.setHasMoved(true);
        Assertions.assertFalse(pawn1.findValidWhitePath(3, 1, 3, 3));
    }

    @Test
    public void testFindValidWhitePathDoublePass() {
        Assertions.assertTrue(pawn1.findValidWhitePath(3, 1, 3, 3));
    }

    @Test
    public void testFindValidBlackPathAlreadyMovedFail() {
        pawn2.setHasMoved(true);
        Assertions.assertFalse(pawn2.findValidBlackPath(3, 6, 3, 4));
    }
}
