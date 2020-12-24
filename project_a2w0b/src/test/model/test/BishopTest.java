package model.test;

import model.Board;
import model.Color;
import model.pieces.Bishop;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BishopTest {
    Board board;
    Bishop bishop;

    @BeforeEach
    public void runBefore() {
        board = new Board();
        bishop = new Bishop(Color.WHITE, board);
        board.addPiece(bishop, board.getSquares()[3][1]);
    }
    @Test
    public void testConstructor() {
        Assertions.assertEquals(Color.WHITE, bishop.getColor());
        Assertions.assertEquals(board, bishop.getBoard());
    }

    @Test
    public void testFindPath() {
        Assertions.assertTrue(bishop.findPath(board.getSquares()[4][2]));
        Assertions.assertFalse(bishop.findPath(board.getSquares()[5][2]));
    }

    @Test
    public void testFindValidPathInvalidDestination() {
        Assertions.assertTrue(bishop.findPath(board.getSquares()[4][2]));
        Assertions.assertFalse(bishop.findPath(board.getSquares()[5][2]));
    }
    @Test
    public void testFindValidPathUpRight() {
        Assertions.assertTrue(bishop.findPath(board.getSquares()[4][2]));
    }

    @Test
    public void testFindValidPathUpLeft() {
        Assertions.assertTrue(bishop.findPath(board.getSquares()[2][2]));
    }

    @Test
    public void testFindValidPathDownRight() {
        Assertions.assertTrue(bishop.findPath(board.getSquares()[4][0]));
    }
    @Test
    public void testFindValidPathDownLeft() {
        Assertions.assertTrue(bishop.findPath(board.getSquares()[2][0]));
    }

    @Test
    public void testFindValidPathLongDiagonal() {
        Assertions.assertTrue(bishop.findPath(board.getSquares()[6][4]));
    }

}
