package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Square class represents a square on the chessboard which can be clicked as part of a move
public class SquareButton extends JButton implements ActionListener {

    private int row;
    private int column;
    private GUI board;

    // EFFECTS: initializes a square with a given row and column on the associated board and
    // adds an action listener to it
    public SquareButton(int row, int column, GUI board) {
        this.row = row;
        this.column = column;
        this.board = board;
        addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS:  when this is clicked, indicates to the board whether this is the first or second square in a proposed
    // move. If it is the second square, attempt to make the move.
    public void actionPerformed(ActionEvent e) {
        if (board.getSquare1() == null) {
            board.setSquare1(this);
        } else if (board.getSquare2() == null) {
            board.setSquare2(this);
            board.makeMove();
        } else {
            return;
        }
    }

    // EFFECTS: returns the coordinates of the current square as an integer array;
    public int[] getCoordinates() {
        int[] coordinates = new int[2];
        coordinates[0] = row;
        coordinates[1] = column;
        return coordinates;
    }

}
