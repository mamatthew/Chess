package model;

import model.pieces.King;
import model.pieces.Pawn;
import model.pieces.Piece;

import java.util.List;
/* The Board class represents an 8 x 8 chessboard with 64 squares and a white and black player */

public class Board {

    private Square[][] squares;
    private Player white;
    private Player black;

    // MODIFIES: this
    // EFFECTS: creates an 8 x 8 matrix of squares
    public Board() {
        squares = new Square[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Square square = new Square(i, j);
                squares[i][j] = square;
            }
        }
    }


    public Player getWhite() {
        return white;
    }

    public void setWhite(Player white) {
        this.white = white;
    }

    public Player getBlack() {
        return black;
    }

    public void setBlack(Player black) {
        this.black = black;
    }

    public Square[][] getSquares() {
        return squares;
    }

    // EFFECTS: determines whether both x and y are between 0 and 7 (inclusive)
    public boolean containsSquare(int x, int y) {
        return (0 <= x && x <= 7) && (0 <= y && y <= 7);
    }

    //REQUIRES: square exists on the board
    //MODIFIES: this, p, square
    //EFFECTS: adds a piece p to a given square on the board
    public void addPiece(Piece p, Square square) {
        if (square.getPiece() == null) {
            square.setPiece(p);
            p.setSquare(square);
            p.setBoard(this);
        }
    }

    //REQUIRES: start and end end are on the board
    //MODIFIES: start, end, this
    //EFFECTS: moves piece from start to end, removing any pieces on end
    //returns false if there is not piece on start
    public boolean movePiece(Square start, Square end) {
        if (start.getPiece() == null) {
            return false;
        } else {
            if (end.getPiece() != null) {
                removePiece(end);
            }
            addPiece(start.getPiece(), end);
            start.setPiece(null);
            return true;
        }
    }

    //REQUIRES: square exists in this and squares contains a piece
    //MODIFIES: this, square
    //EFFECTS: removes piece from square and adds it to the appropriate player's collection,
    // depending on the piece color (black player if piece is black, white player if piece is white
    public void removePiece(Square square) {
        Piece piece = square.getPiece();
        if ((piece.getColor() == Color.WHITE) && white != null) {
            black.addToCaptured(piece);
        } else if ((piece.getColor() == Color.BLACK) && black != null) {
            white.addToCaptured(piece);
        }
        piece.setSquare(null);
        piece.setBoard(null);
        square.setPiece(null);
    }

    //REQUIRES: square exists in squares
    //EFFECTS: returns the piece on square
    public Piece checkSquare(Square square) {
        int x = square.getRow();
        int y = square.getColumn();
        return this.getSquares()[x][y].getPiece();
    }

    //REQUIRES: path represents a path for a type of chess piece
    //MODIFIES: this
    //EFFECTS: moves piece from first square in path to last square in path and return true if
    // it is valid to do so; return false otherwise
    public boolean isValidMove(List<Square> path) {
        if (path.get(0).getPiece() instanceof Pawn) {
            if (isPawnImpeded(path)) {
                return false;
            }
        }
        if (path.get(0).getPiece() instanceof King) {
            if (!isLegalKingMove(path)) {
                return false;
            }
            movePiece(path.get(0), path.get(path.size() - 1));
            return true;
        }
        for (int i = 1; i < path.size(); i++) {
            Square square = path.get(i);
            if (path.get(i).getPiece() == null) {
                continue;
            }
            if (path.get(0).getPiece().getColor() == square.getPiece().getColor()) {
                return false;
            }
        }
        movePiece(path.get(0), path.get(path.size() - 1));
        return true;
    }

    //REQUIRES: path represents a path for a King
    //EFFECTS: returns true if path is a legal path for a king;
    // return false otherwise
    public boolean isLegalKingMove(List<Square> path) {
        King piece = (King) path.get(0).getPiece();
        for (int i = 1; i < path.size(); i++) {
            Square square = path.get(i);
            if ((square.getPiece() != null) && (piece.getColor() == square.getPiece().getColor())) {
                return false;
            }
            if (piece.isInCheck(square)) {
                return false;
            }
        }
        return true;
    }

    //REQUIRES: path represents a vertical path for a pawn
    //MODIFIES: this
    //EFFECTS: moves pawn at start of path to end of path and return true if it isn't impeded by another piece
    // along the way; return false otherwise
    private boolean isPawnImpeded(List<Square> path) {
        if (Math.abs(path.get(path.size() - 1).getRow() - path.get(0).getRow()) != 0) {
            return false;
        } else {
            for (int i = 1; i < path.size(); i++) {
                if (path.get(i).getPiece() != null) {
                    return true;
                }
            }
        }
        return false;
    }

    //REQUIRES: king must be able to castle
    //MODIFIES: king, this
    //EFFECTS: castles king kingside
    public void castleKingSide(King king) {
        if (king.getColor() == Color.WHITE) {
            movePiece(king.getSquare(), squares[6][0]);
            movePiece(squares[7][0], squares[5][0]);
        } else {
            movePiece(king.getSquare(), squares[6][7]);
            movePiece(squares[7][7], squares[5][7]);
        }
    }

    //REQUIRES: king must be able to castle
    //MODIFIES: king, this
    //EFFECTS: castles king queenside
    public void castleQueenSide(King king) {
        if (king.getColor() == Color.WHITE) {
            movePiece(king.getSquare(), squares[2][0]);
            movePiece(squares[0][0], squares[3][0]);
        } else {
            movePiece(king.getSquare(), squares[2][7]);
            movePiece(squares[0][7], squares[3][7]);
        }
    }


}
