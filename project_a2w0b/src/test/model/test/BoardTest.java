package model.test;

import model.Board;
import model.Color;
import model.Player;
import model.Square;
import model.pieces.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    private Board board;
    private Piece piece1;
    private Piece piece2;
    private Square square1;
    private Square square2;
    private Player white;
    private Player black;
    @BeforeEach
    public void runBefore() {
        board = new Board();
        piece1 = new Queen(Color.WHITE, board);
        piece2 = new Queen(Color.BLACK, board);
        square1 = board.getSquares()[0][0];
        square2 = board.getSquares()[1][1];
        white = new Player("Kevin", new ArrayList<>());
        black = new Player("Matt", new ArrayList<>());
        board.setWhite(white);
        board.setBlack(black);

    }

    @Test
    public void testConstructor() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Square square = board.getSquares()[i][j];
                Assertions.assertEquals(i, square.getRow());
                Assertions.assertEquals(j, square.getColumn());
            }
        }
    }

    @Test
    public void testSetWhite() {
        Player player;
        board.setBlack(player = new Player("Kevin", new ArrayList<>()));
        assertTrue(board.getBlack().equals(player));
    }

    @Test
    public void testSetBlack() {
        Player player;
        board.setWhite(player = new Player("Kevin", new ArrayList<>()));
        assertTrue(board.getWhite().equals(player));
    }
    @Test
    public void testContainsSquareTrue() {
        Assertions.assertTrue(board.containsSquare(0,0));
    }

    @Test
    public void testContainsSquareFalse() {
        Assertions.assertFalse(board.containsSquare(-1,-1));
    }

    @Test
    public void testAddPieceEmptySquare() {
        board.addPiece(piece1, square1);
        Assertions.assertTrue(square1.getPiece().equals(piece1));
        Assertions.assertTrue(piece1.getSquare().equals(square1));
    }
    @Test
    public void testAddPieceOccupiedSquare() {
        board.addPiece(piece1, square1);
        board.addPiece(piece2, square1);
        Assertions.assertTrue(square1.getPiece().equals(piece1));
        Assertions.assertTrue(piece1.getSquare().equals(square1));
        Assertions.assertEquals(null, piece2.getSquare());
    }

    @Test
    public void testRemovePieceWhite() {
        board.addPiece(piece1, square1);
        board.removePiece(square1);
        Assertions.assertEquals(null, square1.getPiece());
        Assertions.assertEquals(null, piece1.getSquare());
        assertTrue(board.getBlack().getCapturedPieces().get(0).equals(piece1));
    }

    @Test
    public void testRemovePieceBlack() {
        board.addPiece(piece2, square1);
        board.removePiece(square1);
        Assertions.assertEquals(null, square1.getPiece());
        Assertions.assertEquals(null, piece2.getSquare());
        assertTrue(board.getWhite().getCapturedPieces().get(0).equals(piece2));
    }

    @Test
    public void testMovePieceEmptyStart() {
        Square square2 = board.getSquares()[1][1];
        Assertions.assertFalse(board.movePiece(square1, square2));
    }

    @Test
    public void testMovePieceEmptyEnd() {
        board.addPiece(piece1, square1);
        board.movePiece(square1, square2);
        Assertions.assertEquals(square2, piece1.getSquare());
        Assertions.assertEquals(piece1, square2.getPiece());
        Assertions.assertEquals(null, square1.getPiece());
    }

    @Test
    public void testMovePieceOccupiedEnd() {
        board.addPiece(piece1, square1);
        board.addPiece(piece2, square2);
        board.movePiece(square1, square2);

        Assertions.assertEquals(piece1, square2.getPiece());
        Assertions.assertEquals(square2, piece1.getSquare());
        Assertions.assertEquals(null, piece2.getSquare());
    }

    @Test
    public void testIsLegalKingMoveFailSameColor() {
        List<Square> path = new ArrayList<>();
        Piece king = new King(Color.WHITE, board);
        Piece knight = new Knight(Color.WHITE, board);
        board.addPiece(king, square1);
        board.addPiece(knight, square2);
        path.add(square1);
        path.add(square2);
        Assertions.assertFalse(board.isLegalKingMove(path));

    }

    @Test
    public void testIsLegalKingMoveCheck() {
        List<Square> path = new ArrayList<>();
        Piece king = new King(Color.WHITE, board);
        Piece knight = new Knight(Color.BLACK, board);
        Square square = board.getSquares()[3][2];
        board.addPiece(king, square1);
        board.addPiece(knight, square);
        path.add(square1);
        path.add(square2);
        Assertions.assertFalse(board.isLegalKingMove(path));

    }

    @Test
    public void testIsValidMoveFailSameColor() {
        List<Square> path = new ArrayList<>();
        Piece piece = new Queen(Color.WHITE, board);
        board.addPiece(piece1, square1);
        board.addPiece(piece, square2);
        path.add(square1);
        path.add(square2);
        Assertions.assertFalse(board.isValidMove(path));
    }
    @Test
    public void testCheckSquare() {
        Assertions.assertNull(board.checkSquare(board.getSquares()[0][0]));
    }

    @Test
    public void testCastleQueensideWhite() {
        King king2 = new King(Color.WHITE, board);
        board.addPiece(king2, board.getSquares()[4][0]);
        Rook rook2 = new Rook(Color.WHITE, board);
        board.addPiece(rook2, board.getSquares()[0][0]);
        board.castleQueenSide(king2);
        Assertions.assertTrue(board.getSquares()[2][0].getPiece().equals(king2));
        Assertions.assertTrue(board.getSquares()[3][0].getPiece().equals(rook2));
    }
    @Test
    public void testCastleKingsideWhite() {
        King king2 = new King(Color.WHITE, board);
        board.addPiece(king2, board.getSquares()[4][0]);
        Rook rook2 = new Rook(Color.WHITE, board);
        board.addPiece(rook2, board.getSquares()[7][0]);
        board.castleKingSide(king2);
        Assertions.assertTrue(board.getSquares()[6][0].getPiece().equals(king2));
        Assertions.assertTrue(board.getSquares()[5][0].getPiece().equals(rook2));
    }

    @Test
    public void testCastleQueensideBlack() {
        King king2 = new King(Color.BLACK, board);
        board.addPiece(king2, board.getSquares()[4][7]);
        Rook rook2 = new Rook(Color.BLACK, board);
        board.addPiece(rook2, board.getSquares()[0][7]);
        board.castleQueenSide(king2);
        Assertions.assertTrue(board.getSquares()[2][7].getPiece().equals(king2));
        Assertions.assertTrue(board.getSquares()[3][7].getPiece().equals(rook2));
    }
    @Test
    public void testCastleKingsideBlack() {
        King king2 = new King(Color.BLACK, board);
        board.addPiece(king2, board.getSquares()[4][7]);
        Rook rook2 = new Rook(Color.BLACK, board);
        board.addPiece(rook2, board.getSquares()[7][7]);
        board.castleKingSide(king2);
        Assertions.assertTrue(board.getSquares()[6][7].getPiece().equals(king2));
        Assertions.assertTrue(board.getSquares()[5][7].getPiece().equals(rook2));
    }
}
