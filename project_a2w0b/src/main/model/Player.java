package model;

import model.pieces.Piece;


import java.util.ArrayList;
import java.util.List;

/* Player represents a player in a chess game*/
public class Player {
    private List<Piece> controlledPieces;
    private List<Piece> capturedPieces;
    private String name;
    private int material;


    // EFFECTS: constructs a new player with a given name, 0 material, a list of starting pieces, and an empty list
    // of captured pieces
    public Player(String name, List<Piece> controlledPieces) {
        this.name = name;
        material = 0;
        this.controlledPieces = controlledPieces;
        capturedPieces = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds given piece to list of captured pieces
    public void addToCaptured(Piece piece) {
        capturedPieces.add(piece);
    }

    public List<Piece> getControlledPieces() {
        return controlledPieces;
    }

    public List<Piece> getCapturedPieces() {
        return capturedPieces;
    }

    public String getName() {
        return name;
    }

    public int getMaterial() {
        return material;
    }

    // MODIFIES: this
    // EFFECTS: adds up the numerical value of each captured piece
    public int calculateMaterial() {
        int newMaterial = 0;
        for (int i = 0; i < capturedPieces.size(); i++) {
            newMaterial += capturedPieces.get(i).getValue();
        }
        if (newMaterial > material) {
            material = newMaterial;
        }
        return material;
    }


}
