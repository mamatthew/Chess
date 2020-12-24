package model.pieces;

import model.Board;
import model.Color;
import model.Square;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/* King represents the king piece in chess. The king can move and capture in any direction one unit away
* The king cannot move into a square where will be attacked. The king can also castle if it hasn't moved
*/
public class King extends Piece {

    public static Type type = Type.KING;
    public static final int value = 1000;
    private boolean canCastle;

    //EFFECTS: initializes a king with a color and associates it with a board
    public King(Color color, Board board) {
        super(color, board);
        canCastle = true;
    }

    public boolean getCanCastle() {
        return canCastle;
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

    //REQUIRES: square must exist on board
    //EFFECTS: checks if any piece of opposite color is attacking this
    public boolean isInCheck(Square square) {
        return (diagonalAttack(square) || knightAttack(square) || rankFileAttack(square) || pawnAttack(square));
    }

    //REQUIRES: square must exist on board
    //EFFECTS: checks if a pawn is attacking square
    public boolean pawnAttack(Square square) {
        int x = square.getRow();
        int y = square.getColumn();
        List<Square> paths = new ArrayList<>();
        if (color == Color.BLACK) {
            if (board.containsSquare(x - 1, y - 1)) {
                paths.add(board.getSquares()[x - 1][y - 1]);
            }
            if (board.containsSquare(x + 1, y - 1)) {
                paths.add(board.getSquares()[x + 1][y - 1]);
            }
        } else {
            if (board.containsSquare(x + 1, y + 1)) {
                paths.add(board.getSquares()[x + 1][y + 1]);
            }
            if (board.containsSquare(x - 1, y + 1)) {
                paths.add(board.getSquares()[x - 1][y + 1]);
            }
        }
        return checkPawnAttack(paths);
    }

    //REQUIRES: path must be a path that can be taken by a pawn
    //EFFECTS: checks if a pawn is attacking from the squares in paths
    public boolean checkPawnAttack(List<Square> paths) {
        for (int i = 0; i < paths.size(); i++) {
            Piece piece = board.checkSquare(paths.get(i));
            if (piece == null) {
                continue;
            }
            if (piece.getColor() == getColor()) {
                return false;
            }
            if ((piece instanceof Pawn)) {
                return true;
            }
        }
        return false;
    }

    //REQUIRES: square must exist on board
    //EFFECTS: checks if a piece is attacking square vertically or horizontally
    public boolean rankFileAttack(Square square) {
        int x = square.getRow();
        int y = square.getColumn();
        List<Square> paths = new ArrayList<>();
        paths = checkRight(paths, x, y);
        paths = checkUp(paths, x, y);
        paths = checkLeft(paths, x, y);
        paths = checkDown(paths, x, y);
        return checkRankFileAttack(paths);
    }

    //REQUIRES: paths must be a horizontal path to the left of square (x,y)
    //MODIFIES: paths
    //EFFECTS: checks if a piece is attacking square (x,y) along path
    private List<Square> checkLeft(List<Square> paths, int x, int y) {
        for (int i = 1; i <= (7 - x); i++) {
            if (board.containsSquare(x - i, y)) {
                Piece piece = board.checkSquare(board.getSquares()[x - i][y]);
                if (piece == null) {
                    continue;
                }
                if (piece.getColor() == getColor()) {
                    break;
                }
                paths.add(board.getSquares()[x - i][y]);
            }
        }
        return paths;
    }

    //REQUIRES: paths must be a vertical path above of square (x,y)
    //MODIFIES: paths
    //EFFECTS: checks if a piece is attacking square (x,y) along path
    private List<Square> checkUp(List<Square> paths, int x, int y) {
        for (int i = 1; i <= (7 - y); i++) {
            if (board.containsSquare(x, y + i)) {
                Piece piece = board.checkSquare(board.getSquares()[x][y + i]);
                if (piece == null) {
                    continue;
                }
                if (piece.getColor() == getColor()) {
                    break;
                }
                paths.add(board.getSquares()[x][y + i]);
            }
        }
        return paths;
    }

    //REQUIRES: paths must be a vertical path below square (x,y)
    //MODIFIES: paths
    //EFFECTS: checks if a piece is attacking square (x,y) along path
    private List<Square> checkDown(List<Square> paths, int x, int y) {
        for (int i = 1; i <= (7 - y); i++) {
            if (board.containsSquare(x, y - i)) {
                Piece piece = board.checkSquare(board.getSquares()[x][y - i]);
                if (piece == null) {
                    continue;
                }
                if (piece.getColor() == getColor()) {
                    break;
                }
                paths.add(board.getSquares()[x][y - i]);
            }
        }
        return paths;
    }

    //REQUIRES: paths must be a horizontal path to the right of square (x,y)
    //MODIFIES: paths
    //EFFECTS: checks if a piece is attacking square (x,y) along path
    private List<Square> checkRight(List<Square> paths, int x, int y) {
        for (int i = 1; i <= (7 - x); i++) {
            if (board.containsSquare(x + i, y)) {
                Piece piece = board.checkSquare(board.getSquares()[x + i][y]);
                if (piece == null) {
                    continue;
                }
                if (piece.getColor() == getColor()) {
                    break;
                }
                paths.add(board.getSquares()[x + i][y]);
            }
        }
        return paths;
    }

    //REQUIRES: path must be horizontal or vertical
    //EFFECTS: checks if a piece is attacking square vertically or horizontally
    public boolean checkRankFileAttack(List<Square> paths) {
        for (int i = 0; i < paths.size(); i++) {
            Square square = paths.get(i);
            Piece piece = board.checkSquare(square);
            if ((piece instanceof Queen) || (piece instanceof Rook)) {
                return true;
            }
        }
        return false;
    }

    //REQUIRES: square must exist on board
    //EFFECTS: checks if a knight is attacking square
    public boolean knightAttack(Square square) {
        int x = square.getRow();
        int y = square.getColumn();
        List<Square> paths = new ArrayList<>();
        getKnightSquares(square, paths, x, y);
        return checkKnightAttack(getKnightSquares(square, paths, x, y));
    }

    //REQUIRES: square (x,y) must exist on board
    //MODIFIES: paths
    //EFFECTS: return list of all possible locations a knight could be attacking square(x,y)
    public List<Square> getKnightSquares(Square square, List<Square> paths, int x, int y) {
        if (board.containsSquare(x + 1, y + 2)) {
            paths.add(board.getSquares()[x + 1][y + 2]);
        }
        if (board.containsSquare(x + 1, y - 2)) {
            paths.add(board.getSquares()[x + 1][y - 2]);
        }
        if (board.containsSquare(x - 1, y + 2)) {
            paths.add(board.getSquares()[x - 1][y + 2]);
        }
        if (board.containsSquare(x - 1, y - 2)) {
            paths.add(board.getSquares()[x - 1][y - 2]);
        }
        if (board.containsSquare(x + 2, y + 1)) {
            paths.add(board.getSquares()[x + 2][y + 1]);
        }
        if (board.containsSquare(x + 2, y - 1)) {
            paths.add(board.getSquares()[x + 2][y - 1]);
        }
        if (board.containsSquare(x - 2, y + 1)) {
            paths.add(board.getSquares()[x - 2][y + 1]);
        }
        return getFinalSquare(paths, x, y);
    }

    //REQUIRES: square (x,y) must exist on board and paths are squares where knights could be attacking
    //MODIFIES: paths
    //EFFECTS: adds potential attacking knight square (2 left, 1 down) to paths
    private List<Square> getFinalSquare(List<Square> paths, int x, int y) {
        if (board.containsSquare(x - 2,y - 1)) {
            paths.add(board.getSquares()[x - 2][y - 1]);
        }
        return paths;
    }

    //REQUIRES: paths must be squares where knights could be attacking
    //EFFECTS: checks if squares in paths are occupied by opposing knights
    public boolean checkKnightAttack(List<Square> paths) {
        for (int i = 0; i < paths.size(); i++) {
            Square square = paths.get(i);
            Piece piece = board.checkSquare(square);
            if (piece == null) {
                continue;
            }
            if (piece.getColor() == getColor()) {
                return false;
            }
            if (piece instanceof Knight) {
                return true;
            }
        }
        return false;
    }

    //REQUIRES: square must exist on board
    //EFFECTS: checks if a piece is attacking square diagonally
    public boolean diagonalAttack(Square square) {
        int x = square.getRow();
        int y = square.getColumn();
        List<Square> paths = new ArrayList<>();
        paths = checkUpRightDiagonals(paths, x, y);
        paths = checkUpLeftDiagonals(paths, x, y);
        paths = checkDownRightDiagnonals(paths, x, y);
        paths = checkDownLeftDiagonals(paths, x, y);
        return checkDiagonalAttack(paths);
    }

    //REQUIRES: paths must be an upright diagonal path above of square (x,y)
    //MODIFIES: paths
    //EFFECTS: adds upright diagonal squares where pieces could be attacking square(x,y) to paths
    private List<Square> checkUpRightDiagonals(List<Square> paths, int x, int y) {
        for (int i = 1; i < 8; i++) {
            if (board.containsSquare(x + i, y + i)) {
                Piece piece = board.checkSquare(board.getSquares()[x + i][y + i]);
                if (piece == null) {
                    continue;
                }
                if (getColor() == piece.getColor()) {
                    break;
                }
                paths.add(board.getSquares()[x + i][y + i]);
            }
        }
        return paths;
    }

    //REQUIRES: paths must be an upleft diagonal path above of square (x,y)
    //MODIFIES: paths
    //EFFECTS: adds upleft diagonal squares where pieces could be attacking square(x,y) to paths
    private List<Square> checkUpLeftDiagonals(List<Square> paths, int x, int y) {
        for (int i = 1; i < 8; i++) {
            if (board.containsSquare(x - i, y + i)) {
                Piece piece = board.checkSquare(board.getSquares()[x - i][y + i]);
                if (piece == null) {
                    continue;
                }
                if (getColor() == piece.getColor()) {
                    break;
                }
                paths.add(board.getSquares()[x - i][y + i]);
            }
        }
        return paths;
    }

    //REQUIRES: paths must be an downright diagonal path above of square (x,y)
    //MODIFIES: paths
    //EFFECTS: adds downright diagonal squares where pieces could be attacking square(x,y) to paths
    private List<Square> checkDownRightDiagnonals(List<Square> paths, int x, int y) {
        for (int i = 1; i < 8; i++) {
            if (board.containsSquare(x + i, y - i)) {
                Piece piece = board.checkSquare(board.getSquares()[x + i][y - i]);
                if (piece == null) {
                    continue;
                }
                if (getColor() == piece.getColor()) {
                    break;
                }
                paths.add(board.getSquares()[x + i][y - i]);
            }
        }
        return paths;
    }

    //REQUIRES: paths must be an downleft diagonal path above of square (x,y)
    //MODIFIES: paths
    //EFFECTS: adds downleft diagonal squares where pieces could be attacking square(x,y) to paths
    private List<Square> checkDownLeftDiagonals(List<Square> paths, int x, int y) {
        for (int i = 1; i < 8; i++) {
            if (board.containsSquare(x - i, y - i)) {
                Piece piece = board.checkSquare(board.getSquares()[x - i][y - i]);
                if (piece == null) {
                    continue;
                }
                if (getColor() == piece.getColor()) {
                    break;
                }
                paths.add(board.getSquares()[x - i][y - i]);
            }
        }
        return paths;
    }

    //REQUIRES: paths must contain squares where bishops or queens could be attacking this
    //EFFECTS: determines if squares in paths are occupied by opposing bishop or queen
    public boolean checkDiagonalAttack(List<Square> paths) {
        for (int i = 0; i < paths.size(); i++) {
            Square square = paths.get(i);
            Piece piece = board.checkSquare(square);
            if ((piece instanceof Queen) || (piece instanceof Bishop)) {
                return true;
            }
        }
        return false;
    }

    //REQUIRES: (x1,y1) and (x2,y2) must exist in board
    //MODIFIES: this
    //EFFECTS: moves King from (x1,y1) to (x2,y2) and return true if possible
    // return false otherwise
    @Override
    public boolean hasValidPath(int x1, int y1, int x2, int y2) {
        List<Square> path = new ArrayList<>();
        if (((x2 - x1) == 2) && ((y2 - y1) == 0) && canCastle) {
            if (canCastleKingSide()) {
                board.castleKingSide(this);
                return true;
            }
        } else if (((x2 - x1) == -2) && ((y2 - y1) == 0) && canCastle) {
            if (canCastleQueenside()) {
                board.castleQueenSide(this);
                return true;
            }
        }
        if ((Math.abs(y2 - y1) > 1) || (Math.abs(x2 - x1) > 1)
                || ((x1 == x2) && (y2 == y1))) {
            return false;
        }
        path.add(board.getSquares()[x1][y1]);
        path.add(board.getSquares()[x2][y2]);
        if (board.isValidMove(path)) {
            canCastle = false;
            return true;
        }
        return false;
    }

    @Override
    public int getValue() {
        return value;
    }

    //EFFECTS: checks if this can castle kingside
    public boolean canCastleKingSide() {
        if (color == Color.WHITE) {
            if ((board.getSquares()[5][0].getPiece() == null)
                    && (board.getSquares()[6][0].getPiece() == null)
                    && (board.getSquares()[7][0].getPiece() instanceof Rook)
                    && (((Rook) board.getSquares()[7][0].getPiece()).canCastle())) {
                return true;
            }
        } else {
            if ((board.getSquares()[5][7].getPiece() == null)
                    && (board.getSquares()[6][7].getPiece() == null)
                    && (board.getSquares()[7][7].getPiece() instanceof Rook)
                    && (((Rook) board.getSquares()[7][7].getPiece()).canCastle())) {
                return true;
            }
        }
        return false;
    }

    //EFFECTS: checks if this can castle queenside
    public boolean canCastleQueenside() {
        if (color == Color.WHITE) {
            if ((board.getSquares()[3][0].getPiece() == null)
                    && (board.getSquares()[2][0].getPiece() == null)
                    && (board.getSquares()[0][0].getPiece() instanceof Rook)
                    && (((Rook) board.getSquares()[0][0].getPiece()).canCastle())) {
                return true;
            }
        } else {
            if ((board.getSquares()[3][7].getPiece() == null)
                    && (board.getSquares()[2][7].getPiece() == null)
                    && (board.getSquares()[0][7].getPiece() instanceof Rook)
                    && (((Rook) board.getSquares()[0][7].getPiece()).canCastle())) {
                return true;
            }
        }
        return false;
    }
}
