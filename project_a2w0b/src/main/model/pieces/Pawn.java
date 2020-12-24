package model.pieces;

import model.Board;
import model.Color;
import model.Square;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/* Pawn represents the pawn piece in chess. Pawns can move 1 unit forward vertically, and capture diagonally on its
 * upperleft or upperright squares. If it hasn't moved yet, the pawn can move 2 units forward*/

public class Pawn extends Piece {

    public static Type type = Type.PAWN;
    private int value = 1;
    private boolean hasMoved;

    //EFFECTS: initializes new bishop with a color and associates it with a board
    public Pawn(Color color, Board board) {
        super(color, board);
        hasMoved = false;
    }

    public boolean getHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
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
        if (this.color == Color.WHITE) {
            return (findValidWhitePath(x1, y1, x2, y2));
        } else {
            return (findValidBlackPath(x1, y1, x2, y2));
        }
    }

    //REQUIRES: given coordinate must exist on board and first set of coordinates correspond to square
    // occupied by white pawn
    //MODIFIES: this
    //EFFECTS: moves this to destination and return true if possible
    // return false otherwise
    public boolean findValidWhitePath(int x1, int y1, int x2, int y2) {
        if (y2 <= y1) {
            return false;
        } else if (((y2 - y1) == 2) && !hasMoved) {
            if (hasValidPath(x1, y1, x2, y2)) {
                hasMoved = true;
                return true;
            }
        } else {
            if ((((y2 - y1) == 1) && (Math.abs(x2 - x1) == 1)) || (((y2 - y1) == 1) && ((x2 == x1)))) {
                if (hasValidPath(x1, y1, x2, y2)) {
                    hasMoved = true;
                    return true;
                }
            }
        }
        return false;
    }

    //REQUIRES: given coordinate must exist on board and first set of coordinates correspond to square
    // occupied by pawn
    //MODIFIES: this
    //EFFECTS: moves this to destination and return true if possible
    // return false otherwise
    public boolean hasValidPath(int x1, int y1, int x2, int y2) {
        List<Square> path = new ArrayList<>();
        path.add(board.getSquares()[x1][y1]);
        if (this.color == Color.WHITE) {
            if ((y2 - y1) == 2) {
                path.add(board.getSquares()[x1][y1 + 1]);
                path.add(board.getSquares()[x1][y2]);

            } else {
                path.add(board.getSquares()[x2][y2]);
            }
        } else {
            if ((y1 - y2) == 2) {
                path.add(board.getSquares()[x1][y1 - 1]);
                path.add(board.getSquares()[x1][y2]);
            } else {
                path.add(board.getSquares()[x2][y2]);
            }
        }
        return board.isValidMove(path);
    }

    @Override
    public int getValue() {
        return value;
    }

    //REQUIRES: given coordinate must exist on board and first set of coordinates correspond to square
    // occupied by black pawn
    //MODIFIES: this
    //EFFECTS: moves this to destination and return true if possible
    // return false otherwise
    public boolean findValidBlackPath(int x1, int y1, int x2, int y2) {
        if (y2 >= y1) {
            return false;
        } else if (((y1 - y2) == 2) && !hasMoved && (Math.abs(x2 - x1) == 0)) {
            if (hasValidPath(x1, y1, x2, y2)) {
                hasMoved = true;
                return true;
            }
        } else {
            if ((((y1 - y2) == 1) && (Math.abs(x1 - x2) == 1))
                    || (((y2 - y1) == -1) && ((x2 == x1)))) {
                if (hasValidPath(x1, y1, x2, y2)) {
                    hasMoved = true;
                    return true;
                }
            }
        }
        return false;
    }

}
