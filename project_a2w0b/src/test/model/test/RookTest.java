package model.test;

import model.Board;
import model.Color;
import model.Square;
import model.pieces.Rook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class RookTest {

    Board board;
    Rook rook;

    @BeforeEach
    public void runBefore() {
        board = new Board();
        rook = new Rook(Color.WHITE, board);
        board.addPiece(rook, board.getSquares()[2][2]);
    }
    @Test
    public void testConstructor() {
        Assertions.assertEquals(Color.WHITE, rook.getColor());
        Assertions.assertEquals(board, rook.getBoard());
        Assertions.assertTrue(rook.canCastle());
    }

    @Test
    public void testFindPath() {
        Assertions.assertTrue(rook.findPath(board.getSquares()[2][4]));
        Assertions.assertFalse(rook.findPath(board.getSquares()[3][3]));
    }
    @Test
    public void hasValidPathXorFalseFalse() {
        Assertions.assertFalse(rook.hasValidPath(2,2,3,3));
    }
    @Test
    public void hasValidPathXorTrueTrue() {
        Assertions.assertFalse(rook.hasValidPath(2,2,2,2));
    }
    @Test
    public void hasValidPathXorTrueFalse() {
        Assertions.assertTrue(rook.hasValidPath(2,2,4,2));
    }
    @Test
    public void hasValidPathXorFalseTrue() {
        Assertions.assertTrue(rook.hasValidPath(2,2,2,0));
    }

    @Test
    public void hasValidPathCannotCastleAfterMove() {
        Assertions.assertTrue(rook.hasValidPath(2,2,2,0));
        Assertions.assertFalse(rook.canCastle());
    }

    @Test
    public void testGetValue() {
        assertEquals(5, rook.getValue());
    }

    @Test
    public void testIsValidRookMove() {
        List<Square> path = new ArrayList<>();
        path.add(rook.getSquare());
        path.add(rook.getSquare());
        assertFalse(board.isValidMove(path));
    }
    @Test
    public void hasValidPathLongMove() {
        Assertions.assertTrue(rook.hasValidPath(2,2,2,6));
    }
}
