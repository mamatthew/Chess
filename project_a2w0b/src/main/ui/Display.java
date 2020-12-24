package ui;

import model.Player;
import model.pieces.*;

import javax.swing.*;

// Display class represents the text area that displays each player's material, including captured pieces and
// the numerical value of their material

public class Display extends JTextArea {
    Player white;
    Player black;

    //EFFECTS: constructs a new material display with associated white and black players
    public Display(Player white, Player black) {
        super("Material");
        this.white = white;
        this.black = black;
    }

    //EFFECTS: lists out each player's captured pieces and adds them to the display
    public void getMaterial() {
        StringBuilder string = new StringBuilder();
        string.append("White's material: \n");
        for (int i = 0; i < black.getCapturedPieces().size(); i++) {
            string.append(getPiece(black.getCapturedPieces().get(i)));
            string.append("\n");

        }
        string.append("Black's material: \n");
        for (int i = 0; i < white.getCapturedPieces().size(); i++) {
            string.append(getPiece(white.getCapturedPieces().get(i)));
            string.append("\n");

        }
        setText(string.toString());
    }

    //EFFECTS: calculates each player's material value and adds it to the display
    public void calculateMaterial() {
        int whiteMaterial = black.calculateMaterial();
        int blackMaterial = white.calculateMaterial();
        append("\n");
        append("\n");
        append("White: " + whiteMaterial + "\n");
        append("Black: " + blackMaterial);
    }

    //EFFECTS: returns the string representation of a given piece object.
    private String getPiece(Piece piece) {
        if (piece instanceof Pawn) {
            return "pawn";
        } else if (piece instanceof Knight) {
            return "knight";
        } else if (piece instanceof Bishop) {
            return "bishop";
        } else if (piece instanceof Rook) {
            return "rook";
        } else if (piece instanceof Queen) {
            return "queen";
        } else {
            return "king";
        }
    }
}
