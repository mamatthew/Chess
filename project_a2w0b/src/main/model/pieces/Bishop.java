package model.pieces;

import model.Board;
import model.Color;
import model.Square;

import java.util.ArrayList;
import java.util.List;
/* Bishop represents the bishop in chess. Bishops move and capture along diagonals*/

public class Bishop extends Piece {

    public static Type type = Type.BISHOP;
    public int value = 3;


    //EFFECTS: initializes new bishop with a color and associates it with a board
    public Bishop(Color color, Board board) {
        super(color, board);
    }

    //REQUIRES: destination must exist on board
    //MODIFIES: this
    //EFFECTS: moves this to destination and return true if possible
    // return false otherwise
    @Override
    public boolean findPath(Square destination) {
        int x1 = square.getRow();
        int y1 = square.getColumn();
        int x2 = destination.getRow();
        int y2 = destination.getColumn();
        return (hasValidPath(x1,y1,x2,y2));
    }

    //REQUIRES: (x1, y1) and (x2, y2) exist on board
    //MODIFIES: this
    //EFFECTS: moves this to destination and return true if possible
    // return false otherwise
    @Override
    public boolean hasValidPath(int x1, int y1, int x2, int y2) {
        int dirX = 1;
        int dirY = 1;
        List<Square> path = new ArrayList<>();
        if (Math.abs(y2 - y1) != Math.abs(x2 - x1)) {
            return false;
        } else if (((y2 - y1) > 0) && ((x2 - x1) < 0)) {
            dirX = -1;
        } else if (((y2 - y1) < 0) && ((x2 - x1) > 0)) {
            dirY = -1;
        } else if (((y2 - y1) < 0) && ((x2 - x1) < 0)) {
            dirX = -1;
            dirY = -1;
        }
        path.add(board.getSquares()[x1][y1]);
        for (int i = 1; i <= Math.abs(y2 - y1); i++) {
            Square square = board.getSquares()[x1 + i * dirX][y1 + dirY * i];
            path.add(square);
        }
        return board.isValidMove(path);
    }

    @Override
    public int getValue() {
        return value;
    }
}
