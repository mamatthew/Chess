package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// RestoreButton represents a JButton that can be clicked to restored the previous saved board state
public class RestoreButton extends JButton implements ActionListener {

    Game game;

    // EFFECTS: initializes the restore button with given name, associated game, and adds action listener to it
    public RestoreButton(String name, Game game) {
        super(name);
        this.game = game;
        addActionListener(this);
    }

    // EFFECTS: when this is clicked, restore the previous saved board state
    @Override
    public void actionPerformed(ActionEvent e) {
        game.restoreBoard();
    }
}
