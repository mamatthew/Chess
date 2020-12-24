package model.pieces;

import model.Board;
import model.Color;
import model.Square;

import java.util.ArrayList;
import java.util.List;
/*Queen represents the queen piece in chess. Queens can move and capture and distance vertically, horizontally,
and diagonally in any direction */

public class Queen extends Piece {

    public static Type type = Type.QUEEN;
    public int value = 9;

    //EFFECTS: initializes new queen with a color and associates it with a board
    public Queen(Color color, Board board) {
        super(color, board);

    }

    //REQUIRES: square must exist on board
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

    //REQUIRES: squares (x1, y1) and (x2, y2) exist on board
    //MODIFIES: this
    //EFFECTS: moves this from (x1, y1) to (x2, y2) and return true if possible
    // return false otherwise
    @Override
    public boolean hasValidPath(int x1, int y1, int x2, int y2) {
        int dirX;
        int dirY;
        int[] unitDirections;
        List<Square> path = new ArrayList<>();
        if (Math.abs(y2 - y1) == Math.abs(x2 - x1)) {
            unitDirections = getBishopDirection(x1, y1, x2, y2);
        } else if (((y2 - y1) == 0) ^ ((x2 - x1) == 0)) {
            unitDirections = getRookDirection(x1, y1, x2, y2);
        } else {
            return false;
        }
        dirX = unitDirections[0];
        dirY = unitDirections[1];
        int distance = (dirX == 0) ? Math.abs(y2 - y1) : Math.abs(x2 - x1);
        path.add(board.getSquares()[x1][y1]);
        for (int i = 1; i <= distance; i++) {
            Square square = board.getSquares()[x1 + i * dirX][y1 + dirY * i];
            path.add(square);
        }
        return board.isValidMove(path);
    }

    @Override
    public int getValue() {
        return value;
    }

    //REQUIRES: given coordinates must exist on board and be connected diagonally
    //EFFECTS: given starting (x1,y1) and ending coordinates (x2,y2), return the unit vector
    // representing the direction needed to travel diagonally to the end position
    public int[] getBishopDirection(int x1, int y1, int x2, int y2) {
        int[] unitDirections = new int[2];
        if (((y2 - y1) > 0) && ((x2 - x1) < 0)) {
            unitDirections[0] = -1;
            unitDirections[1] = 1;
        } else if (((y2 - y1) < 0) && ((x2 - x1) > 0)) {
            unitDirections[0] = 1;
            unitDirections[1] = -1;
        } else if (((y2 - y1) < 0) && ((x2 - x1) < 0)) {
            unitDirections[0] = -1;
            unitDirections[1] = -1;
        } else {
            unitDirections[0] = 1;
            unitDirections[1] = 1;
        }
        return unitDirections;
    }

    //REQUIRES: given coordinates must exist on board and be connected vertically or horizontally
    //EFFECTS: given starting (x1,y1) and ending coordinates (x2,y2), return the unit vector
    // representing the direction needed to travel vertically or horizontally to the end position
    public int[] getRookDirection(int x1, int y1, int x2, int y2) {
        int[] unitDirections = new int[2];
        if (((y2 - y1) == 0) && ((x2 - x1) > 0)) {
            unitDirections[0] = 1;
            unitDirections[1] = 0;
        } else if (((y2 - y1) == 0) && ((x2 - x1) < 0)) {
            unitDirections[0] = -1;
            unitDirections[1] = 0;
        } else if (((y2 - y1) > 0) && ((x2 - x1) == 0)) {
            unitDirections[0] = 0;
            unitDirections[1] = 1;
        } else {
            unitDirections[0] = 0;
            unitDirections[1] = -1;
        }
        return unitDirections;
    }
}
