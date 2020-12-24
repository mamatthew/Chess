package model.pieces;

import model.Board;
import model.Color;
import model.Square;

import java.util.ArrayList;
import java.util.List;
/* Knight represents the knight chess piece. Knights can move and capture in any L shaped direction. The L has to be
* 2 units in any vertical or horizontal direction, followed by 1 unit in any vertical or horizontal direction.
* Knights are not impeded by other pieces along its path
* */

public class Knight extends Piece {

    public static Type type = Type.KNIGHT;
    private int value = 3;

    //EFFECTS: initializes a knight with a color and associates it with a board
    public Knight(Color color, Board board) {
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
    //EFFECTS: moves this to from (x1, y1) to (x2, y2) and return true it is a possible knight movement
    // return false otherwise
    @Override
    public boolean hasValidPath(int x1, int y1, int x2, int y2) {
        List<Square> path = new ArrayList<>();
        if (Math.sqrt(Math.pow((y2 - y1), 2) + Math.pow((x2 - x1), 2)) != Math.sqrt(5)) {
            return false;
        }
        path.add(square);
        path.add(board.getSquares()[x2][y2]);
        return board.isValidMove(path);
    }

    @Override
    public int getValue() {
        return value;
    }
}
