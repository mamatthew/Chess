package model.pieces;

import model.Board;
import model.Color;
import model.Square;

import java.util.ArrayList;
import java.util.List;
/* Rook represents the rook piece in chess. Rooks can move and capture vertically and horizontally in any
* direction. If a rook hasn't moved, it can castle with its king*/

public class Rook extends Piece {

    public static Type type = Type.ROOK;
    public int value = 5;
    private boolean canCastle;

    //EFFECTS: initializes a knight with a color and associates it with a board
    public Rook(Color color, Board board) {
        super(color, board);
        canCastle = true;
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
    //EFFECTS: moves this to from (x1, y1) to (x2, y2) and return true it is a possible rook move
    // return false otherwise
    @Override
    public boolean hasValidPath(int x1, int y1, int x2, int y2) {
        int dirX = 0;
        int dirY = 0;
        List<Square> path = new ArrayList<>();
        if (!((y2 - y1) == 0) ^ ((x2 - x1) == 0)) {
            return false;
        } else if (((y2 - y1) > 0)) {
            dirY = 1;
        } else if (((y2 - y1) < 0)) {
            dirY = -1;
        } else if (((x2 - x1) < 0)) {
            dirX = -1;
        } else {
            dirX = 1;
        }
        int distance = (int) Math.sqrt(Math.pow((y2 - y1),2) + Math.pow((x2 - x1), 2));
        path.add(board.getSquares()[x1][y1]);
        for (int i = 1; i <= distance; i++) {
            Square square = board.getSquares()[x1 + i * dirX][y1 + dirY * i];
            path.add(square);
        }
        return isValidRookMove(path);
    }

    @Override
    public int getValue() {
        return value;
    }

    //REQUIRES: path is a path that can be traveled by a rook
    //MODIFIES: this
    //EFFECTS: if possible, moves this to from start of path to end of path,
    // return false otherwise
    public boolean isValidRookMove(List<Square> path) {
        if (board.isValidMove(path)) {
            canCastle = false;
            return true;
        }
        return false;
    }

    public boolean canCastle() {
        return canCastle;
    }
}
