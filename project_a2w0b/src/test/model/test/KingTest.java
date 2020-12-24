package model.test;

import model.Board;
import model.Color;
import model.pieces.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class KingTest {

    Board board;
    King king;

    @BeforeEach
    public void runBefore() {
        board = new Board();
        king = new King(Color.BLACK, board);
        board.addPiece(king, board.getSquares()[3][3]);
    }
    @Test
    public void testConstructor() {
        Assertions.assertEquals(Color.BLACK, king.getColor());
        Assertions.assertEquals(board, king.getBoard());
        Assertions.assertTrue(king.getCanCastle());

    }
    @Test
    public void testFindPathTrue() {
        Assertions.assertTrue(king.findPath(board.getSquares()[4][4]));
    }
    @Test
    public void testFindPathFalse() {
        Assertions.assertFalse(king.findPath(board.getSquares()[6][4]));
    }

    @Test
    public void testIsInCheckDiagonalAttack() {
        Bishop bishop = new Bishop(Color.WHITE, board);
        board.addPiece(bishop, board.getSquares()[6][6]);
        Assertions.assertTrue(king.isInCheck(king.getSquare()));
    }
    @Test
    public void testIsInCheckRankFileAttack() {
        Rook rook = new Rook(Color.WHITE, board);
        board.addPiece(rook, board.getSquares()[6][3]);
        Assertions.assertTrue(king.isInCheck(king.getSquare()));
    }
    @Test
    public void testIsInCheckKnightAttack() {
        Knight knight = new Knight(Color.WHITE, board);
        board.addPiece(knight, board.getSquares()[5][4]);
        Assertions.assertTrue(king.isInCheck(king.getSquare()));
    }
    @Test
    public void testIsInCheckPawnAttack() {
        Pawn pawn = new Pawn(Color.WHITE, board);
        board.addPiece(pawn, board.getSquares()[2][2]);
        Assertions.assertTrue(king.isInCheck(king.getSquare()));
    }

    @Test
    public void testCanCastleKingSideWhite() {
        King king2 = new King(Color.WHITE, board);
        board.addPiece(king2, board.getSquares()[4][0]);
        Rook rook2 = new Rook(Color.WHITE, board);
        board.addPiece(rook2, board.getSquares()[7][0]);
        Assertions.assertTrue(king2.canCastleKingSide());
    }
    @Test
    public void testCanCastleKingSideBlack() {
        King king2 = new King(Color.BLACK, board);
        board.addPiece(king2, board.getSquares()[4][7]);
        Rook rook2 = new Rook(Color.BLACK, board);
        board.addPiece(rook2, board.getSquares()[7][7]);
        Assertions.assertTrue(king2.canCastleKingSide());
    }
    @Test
    public void testCanCastleQueenSideWhite() {
        King king2 = new King(Color.WHITE, board);
        board.addPiece(king2, board.getSquares()[4][0]);
        Rook rook2 = new Rook(Color.WHITE, board);
        board.addPiece(rook2, board.getSquares()[0][0]);
        Assertions.assertTrue(king2.canCastleQueenside());
    }
    @Test
    public void testCanCastleQueenSideBlack() {
        King king2 = new King(Color.BLACK, board);
        board.addPiece(king2, board.getSquares()[4][7]);
        Rook rook2 = new Rook(Color.BLACK, board);
        board.addPiece(rook2, board.getSquares()[0][7]);
        Assertions.assertTrue(king2.canCastleQueenside());
    }

    @Test
    public void testHasValidPathTooFar() {
        Assertions.assertFalse(king.hasValidPath(3,3,6,6));
    }

    @Test
    public void testHasValidPathWrongStart() {
        Assertions.assertFalse(king.hasValidPath(0,0,0,0));
    }

    @Test
    public void testHasValidPathCanCastleKingside() {
        King king2 = new King(Color.WHITE, board);
        board.addPiece(king2, board.getSquares()[4][0]);
        Rook rook2 = new Rook(Color.WHITE, board);
        board.addPiece(rook2, board.getSquares()[7][0]);
        Assertions.assertTrue(king2.hasValidPath(4,0,6,0));
    }

    @Test
    public void testHasValidPathCanCastleQueenside() {
        King king2 = new King(Color.WHITE, board);
        board.addPiece(king2, board.getSquares()[4][0]);
        Rook rook2 = new Rook(Color.WHITE, board);
        board.addPiece(rook2, board.getSquares()[0][0]);
        Assertions.assertTrue(king2.hasValidPath(4,0,2,0));
    }

    @Test
    public void testSetCanCastle() {
        assertTrue(king.getCanCastle());
    }

    @Test
    public void testGetValue() {
        assertEquals(1000, king.getValue());
    }

}
