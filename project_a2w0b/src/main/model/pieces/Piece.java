package model.pieces;

import model.Board;
import model.Color;
import model.Square;

import java.util.List;
/*Piece is an abstract class representing generic information common to all specific piece types*/

public abstract class Piece {
    protected Color color;
    protected Square square;
    protected Board board;
    public int value;

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }


    //EFFECTS: initializes a piece with a given color and associates it with a board
    public Piece(Color color, Board board) {
        this.color = color;
        this.board = board;
    }

    public Color getColor() {
        return color;
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    //REQUIRES: square must exist on board
    //MODIFIES: this
    //EFFECTS: moves this to destination and return true if possible
    // return false otherwise
    public abstract boolean findPath(Square square);

    //REQUIRES: squares (x1, y1) and (x2, y2) exist on board
    //MODIFIES: this
    //EFFECTS: moves this from (x1, y1) to (x2, y2) and return true if possible
    // return false otherwise
    public abstract boolean hasValidPath(int x1, int y1, int x2, int y2);

    public abstract int getValue();
}
