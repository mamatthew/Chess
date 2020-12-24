package model.test;

import model.Board;
import model.Color;
import model.pieces.Knight;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class KnightTest {
    Board board;
    Knight knight;

    @BeforeEach
    public void runBefore() {
        board = new Board();
        knight = new Knight(Color.WHITE, board);
        board.addPiece(knight, board.getSquares()[2][2]);
    }
    @Test
    public void testConstructor() {
        Assertions.assertEquals(Color.WHITE, knight.getColor());
        Assertions.assertEquals(board, knight.getBoard());
    }

    @Test
    public void testFindPath() {
        Assertions.assertTrue(knight.findPath(board.getSquares()[0][1]));
        Assertions.assertFalse(knight.findPath(board.getSquares()[5][2]));
    }

    @Test
    public void testHasValidPathFail() {
        Assertions.assertFalse(knight.hasValidPath(2,2,5,2));
    }

    @Test
    public void testHasValidPathPass() {
        Assertions.assertTrue(knight.hasValidPath(2,2,3,4));
    }
}
