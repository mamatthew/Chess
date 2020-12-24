package ui;

import model.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Material button represents the button which can be clicked to calculate each player's material as a numerical value
public class MaterialButton extends JButton implements ActionListener {

    Game game;
    Display display;
    Player white;
    Player black;

    //EFFECTS: initializes the material button with a name and associated game, material display, and players
    public MaterialButton(String title, Game game, Display display) {
        super(title);
        this.game = game;
        this.display = display;
        this.white = game.getWhitePlayer();
        this.black = game.getBlackPlayer();
        addActionListener(this);
    }

    //EFFECTS: calculates material for each player for display in the GUI
    @Override
    public void actionPerformed(ActionEvent e) {
        display.calculateMaterial();
    }
}
