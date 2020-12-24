package model;

import model.pieces.Piece;
/* Square class represents a square on a chessboard */

public class Square {
    private Piece piece;
    private int row;
    private int column;

    // REQUIRES: 0 <= row <= 7 and 0 <= column <= 7
    // EFFECTS: constructs a square with a given row and column
    public Square(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
