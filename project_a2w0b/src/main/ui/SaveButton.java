package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// SaveButton represents a JButton which can be clicked to save the current state of the chessboard
public class SaveButton extends JButton implements ActionListener {
    Game game;

    // EFFECTS: initializes the save button with given name, associated game and adds an action listener to it
    public SaveButton(String name, Game game) {
        super(name);
        this.game = game;
        addActionListener(this);
    }

    //EFFECTs saves the current state of the game when clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        game.saveBoard();
    }
}
