package model.test;

import model.Board;
import model.Color;
import model.pieces.Queen;
import model.pieces.Rook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QueenTest {

    Board board;
    Queen queen;

    @BeforeEach
    public void runBefore() {
        board = new Board();
        queen = new Queen(Color.BLACK, board);
        board.addPiece(queen, board.getSquares()[3][3]);
    }
    @Test
    public void testConstructor() {
        Assertions.assertEquals(Color.BLACK, queen.getColor());
        Assertions.assertEquals(board, queen.getBoard());
    }
    @Test
    public void testFindPathTrue() {
        Assertions.assertTrue(queen.findPath(board.getSquares()[3][4]));
        Assertions.assertTrue(queen.findPath(board.getSquares()[5][4]));
    }
    @Test
    public void testFindPathFalse() {
        Assertions.assertFalse(queen.findPath(board.getSquares()[5][4]));
    }

    @Test
    public void testHasValidPathInvalidPath() {
        Assertions.assertFalse(queen.hasValidPath(3,3,4,5));
    }

    @Test
    public void testHasValidPathRookPath() {
        Assertions.assertTrue(queen.hasValidPath(3,3,3,5));
    }
    @Test
    public void testHasValidPathBishopPath() {
        Assertions.assertTrue(queen.hasValidPath(3,3,5,5));
    }

    @Test
    public void testGetBishopDirectionUpRight() {
        int[] pair = new int[2];
        pair[0] = 1;
        pair[1] = 1;
        Assertions.assertTrue(queen.getBishopDirection(3,3,4,4)[0] == 1);
        Assertions.assertTrue(queen.getBishopDirection(3,3,4,4)[1] == 1);
    }
    @Test
    public void testGetBishopDirectionUpLeft() {
        int[] pair = new int[2];
        pair[0] = -1;
        pair[1] = 1;
        Assertions.assertTrue(queen.getBishopDirection(3,3,-2,4)[0] == -1);
        Assertions.assertTrue(queen.getBishopDirection(3,3,-2,4)[1] == 1);
    }
    @Test
    public void testGetBishopDirectionDownRight() {
        int[] pair = new int[2];
        pair[0] = 1;
        pair[1] = -1;
        Assertions.assertTrue(queen.getBishopDirection(3,3,4,-2)[0] == 1);
        Assertions.assertTrue(queen.getBishopDirection(3,3,4,-2)[1] == -1);
    }
    @Test
    public void testGetBishopDirectionDownLeft() {
        int[] pair = new int[2];
        pair[0] = -1;
        pair[1] = -1;
        Assertions.assertTrue(queen.getBishopDirection(3,3,-2,-2)[0] == -1);
        Assertions.assertTrue(queen.getBishopDirection(3,3,-2,-2)[1] == -1);
    }
    @Test
    public void testGetRookDirectionRight() {
        int[] pair = new int[2];
        pair[0] = 1;
        pair[1] = 0;
        Assertions.assertTrue(queen.getRookDirection(3,3,4,3)[0] == 1);
        Assertions.assertTrue(queen.getRookDirection(3,3,4,3)[1] == 0);
    }
    @Test
    public void testGetRookDirectionUp() {
        int[] pair = new int[2];
        pair[0] = 0;
        pair[1] = 1;
        Assertions.assertTrue(queen.getRookDirection(3,3,3,4)[0] == 0);
        Assertions.assertTrue(queen.getRookDirection(3,3,3,4)[1] == 1);
    }
    @Test
    public void testGetRookDirectionLeft() {
        int[] pair = new int[2];
        pair[0] = -1;
        pair[1] = 0;
        Assertions.assertTrue(queen.getRookDirection(3,3,2,3)[0] == -1);
        Assertions.assertTrue(queen.getRookDirection(3,3,2,3)[1] == 0);
    }
    @Test
    public void testGetRookDirectionDown() {
        int[] pair = new int[2];
        pair[0] = 0;
        pair[1] = -1;
        Assertions.assertTrue(queen.getRookDirection(3,3,3,2)[0] == 0);
        Assertions.assertTrue(queen.getRookDirection(3,3,3,2)[1] == -1);
    }
}

