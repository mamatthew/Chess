package ui;

import model.Board;
import model.Color;
import model.Player;
import model.Square;
import model.pieces.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*Game represents the chess game program */

public class Game {
    private Board board;
    private GUI gui;
    private char[][] boardUI;
    private char[][] savedBoard;
    private Player whitePlayer;
    private Player blackPlayer;
    private List<Piece> blackPieces;
    private List<Piece> whitePieces;
    private King whiteKing;
    private King blackKing;
    private boolean isWhiteTurn = true;

    private static final String jsonDir = "./data/board.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: starts the program
    public Game() {
        jsonWriter = new JsonWriter(jsonDir);
        jsonReader = new JsonReader(jsonDir);
        initializePieces();
        initializeBoard();
        initializePlayers();
        gui = new GUI(this);
        eachTurn();
    }

    // EFFECTS: Displays relevant messages to the player at the start of each turn, including:
    // which player's turn it is and whether their king is in check
    private void eachTurn() {
        gui.displayMaterial();
        initializeBoardUI();
        if (isKingInCheck()) {
            if (isWhiteTurn) {
                gui.getMessage().setText("White to move. Your King is in check!");
            } else {
                gui.getMessage().setText("Black to move. Your King is in check!");
            }
        } else {
            if (isWhiteTurn) {
                gui.getMessage().setText("White to move");
            } else {
                gui.getMessage().setText("Black to move");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: saves board state to file
    public void saveBoard() {
        try {
            jsonWriter.open();
            jsonWriter.write(boardUI);
            jsonWriter.close();
            gui.getMessage().setText("Saved the current board state to" + jsonDir);
        } catch (FileNotFoundException e) {
            gui.getMessage().setText("Cannot find file: " + jsonDir);
        }
    }

    // MODIFIES: this
    // EFFECTS: initialized a character matrix representation of board
    public void initializeBoardUI() {
        boardUI = new char[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boardUI[j][i] = getCharFromSquare(board.getSquares()[j][i]);
            }
        }
    }

    // EFFECTS: returns a character representation of a square based on the piece it contains (or lack thereof)
    public char getCharFromSquare(Square square) {
        char c;
        Piece piece = square.getPiece();
        if (piece == null) {
            c = 'O';
            return c;
        }
        if (piece.getColor() == Color.WHITE) {
            c = getCharForWhitePiece(piece);
        } else {
            c = getCharForBlackPiece(piece);
        }
        return c;
    }


    // EFFECTS: returns a character representation for white pieces:
    // rook: R, knight: N, bishop: B, queen: Q, king: K, pawn: P
    private char getCharForWhitePiece(Piece piece) {
        if (piece instanceof Rook) {
            return 'R';
        } else if (piece instanceof Knight) {
            return 'N';
        } else if (piece instanceof Bishop) {
            return 'B';
        } else if (piece instanceof Queen) {
            return 'Q';
        } else if (piece instanceof King) {
            return 'K';
        } else {
            return 'P';
        }
    }

    // EFFECTS: returns a character representation for white pieces:
    // rook: r, knight: n, bishop: b, queen: q, king: k, pawn: p
    private char getCharForBlackPiece(Piece piece) {
        if (piece instanceof Rook) {
            return 'r';
        } else if (piece instanceof Knight) {
            return 'n';
        } else if (piece instanceof Bishop) {
            return 'b';
        } else if (piece instanceof Queen) {
            return 'q';
        } else if (piece instanceof King) {
            return 'k';
        } else {
            return 'p';
        }
    }


    // EFFECTS: determines if the current player's king is in check
    private boolean isKingInCheck() {
        if (blackKing.isInCheck(blackKing.getSquare())) {
            return true;
        } else if (whiteKing.isInCheck(whiteKing.getSquare())) {
            return true;
        }
        return false;
    }

    //MODIFIES: this
    //EFFECTS: moves the piece icon on the on the board from (x1, y1) to (x2, y2)
    //if there is another piece icon at (x2, y2), that piece icon will be removed first
    private void moveIcons(int x1, int y1, int x2, int y2) {
        Icon icon1 = gui.getChessBoardSquares()[x1][y1].getIcon();
        gui.getChessBoardSquares()[x1][y1].setIcon(null);
        if (gui.getChessBoardSquares()[x2][y2].getIcon() != null) {
            gui.getChessBoardSquares()[x2][y2].setIcon(null);
            gui.getChessBoardSquares()[x2][y2].setIcon(icon1);
        } else {
            gui.getChessBoardSquares()[x2][y2].setIcon(icon1);
        }

    }

    // MODIFIES: this
    // EFFECTS: determines if a move from (x1, y1) to (x2, y2) is allowed.
    // if the move is allowed, make the move; if it is not, display a
    // message indicating that
    public void queryDestinationSquare(int x1, int y1, int x2, int y2) {
        if (!isMoveAllowed(x1, y1, x2, y2)) {
            gui.getMessage().setText("That move was invalid");
            gui.setSquare1(null);
            gui.setSquare2(null);
        } else {
            moveIcons(x1, y1, x2, y2);
            gui.setSquare1(null);
            gui.setSquare2(null);
            isWhiteTurn = !isWhiteTurn;
            eachTurn();
        }
    }

    // EFFECTS: determines if a square with coordinates x and y is within the bounds of the board
    private boolean isInBounds(int x, int y) {
        return ((0 <= x && x <= 7) && (0 <= y && y <= 7));
    }

    // MODIFIES: this
    // EFFECTS: determines if a move (x1, y1) to (x2, y2) is allowed
    // moves piece from starting square to destination square if allowed, return false otherwise
    private boolean isMoveAllowed(int x1, int y1, int x2, int y2) {
        Piece piece = board.getSquares()[x1][y1].getPiece();
        Square square = board.getSquares()[x2][y2];
        if (piece == null) {
            return false;
        } else {
            return piece.findPath(square);
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes a set of piece objects of each color
    private void initializePieces() {
        blackPieces = getBlackPieces();
        whitePieces = getWhitePieces();
    }

    // EFFECTS: adds both sets of piece objects (black and white) to the board object
    public void initializeBoard() {
        this.board = new Board();
        for (int i = 0; i < 8; i++) {
            board.addPiece(whitePieces.get(i), board.getSquares()[i][0]);
            board.addPiece(blackPieces.get(i), board.getSquares()[i][7]);
            board.addPiece(whitePieces.get(i + 8), board.getSquares()[i][1]);
            board.addPiece(blackPieces.get(i + 8), board.getSquares()[i][6]);
        }
    }


    // EFFECTS: initializes black and white players
    private void initializePlayers() {
        whitePlayer = new Player("White", whitePieces);
        board.setWhite(whitePlayer);
        blackPlayer = new Player("Black", blackPieces);
        board.setBlack(blackPlayer);
    }

    // MODIFIES: this
    // EFFECTS: initializes the list of black pieces
    public List<Piece> getBlackPieces() {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Rook(Color.BLACK, board));
        pieces.add(new Knight(Color.BLACK, board));
        pieces.add(new Bishop(Color.BLACK, board));
        pieces.add(new Queen(Color.BLACK, board));
        blackKing = new King(Color.BLACK, board);
        pieces.add(blackKing);
        pieces.add(new Bishop(Color.BLACK, board));
        pieces.add(new Knight(Color.BLACK, board));
        pieces.add(new Rook(Color.BLACK, board));
        for (int i = 0; i < 8; i++) {
            pieces.add(new Pawn(Color.BLACK, board));
        }
        return pieces;
    }

    // EFFECTS: initializes list set of black pieces
    public List<Piece> getWhitePieces() {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Rook(Color.WHITE, board));
        pieces.add(new Knight(Color.WHITE, board));
        pieces.add(new Bishop(Color.WHITE, board));
        pieces.add(new Queen(Color.WHITE, board));
        whiteKing = new King(Color.WHITE, board);
        pieces.add(whiteKing);
        pieces.add(new Bishop(Color.WHITE, board));
        pieces.add(new Knight(Color.WHITE, board));
        pieces.add(new Rook(Color.WHITE, board));
        for (int i = 0; i < 8; i++) {
            pieces.add(new Pawn(Color.WHITE, board));
        }
        return pieces;
    }

    // MODIFIES: this
    // EFFECTS: display the previous saved board state
    public void restoreBoard() {
        try {
            savedBoard = jsonReader.read();
            gui.restoreBoardState(savedBoard);
            gui.getMessage().setText("Here is the previous board state from :" + jsonDir);
        } catch (IOException e) {
            gui.getMessage().setText("unable to load saved board state :" + jsonDir);
        }
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }
}
