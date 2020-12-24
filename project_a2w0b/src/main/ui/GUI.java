package ui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;
import java.net.URL;
import javax.imageio.ImageIO;

// GUI represents the graphical user interface for the chess application.
// The code in this class is based on the following post on Stack Overflow:
// https://stackoverflow.com/questions/21077322/create-a-chess-board-with-jpanel
public class GUI extends JFrame {

    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private SquareButton[][] chessBoardSquareButtons = new SquareButton[8][8];
    private Image[][] chessPieceImages = new Image[2][6];
    private JPanel chessBoard;
    private SquareButton squareButton1 = null;
    private SquareButton squareButton2 = null;
    private Game game;
    private Display materialDisplay;
    private SaveButton savebutton;
    private MaterialButton materialButton;
    private RestoreButton restoreButton;

    private JLabel message = new JLabel();
    private static final String COLS = "ABCDEFGH";
    public static final int QUEEN = 0;
    public static final int KING = 1;
    public static final int ROOK = 2;
    public static final int KNIGHT = 3;
    public static final int BISHOP = 4;
    public static final int PAWN = 5;

    public static final int BLACK = 0;
    public static final int WHITE = 1;

    public static final int[] STARTING_ROW = {
            ROOK, KNIGHT, BISHOP, KING, QUEEN, BISHOP, KNIGHT, ROOK
    };


    // initializes the graphical user interface and the JFrame for the chess app
    public GUI(Game game) {
        super("Chess App");
        this.game = game;
        materialDisplay = new Display(game.getWhitePlayer(), game.getBlackPlayer());
        materialDisplay.setLineWrap(true);
        materialDisplay.setBounds(600,600,200,300);
        initializeGui();
        add(gui);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationByPlatform(true);
        pack();
        setMinimumSize(getSize());
        setVisible(true);
    }

    public SquareButton getSquare1() {
        return squareButton1;
    }

    public SquareButton getSquare2() {
        return squareButton2;
    }

    public JLabel getMessage() {
        return message;
    }

    public SquareButton[][] getChessBoardSquares() {
        return this.chessBoardSquareButtons;
    }

    public void setSquare1(SquareButton squareButton1) {
        this.squareButton1 = squareButton1;
    }

    public void setSquare2(SquareButton squareButton2) {
        this.squareButton2 = squareButton2;
    }


    // MODIFIES: this
    // EFFECTS: moves piece from this.square1 to this.square2 if the move is valid
    // if the move is invalid, lets user know
    public void makeMove() {
        int x1 = squareButton1.getCoordinates()[0];
        int y1 = squareButton1.getCoordinates()[1];
        int x2 = squareButton2.getCoordinates()[0];
        int y2 = squareButton2.getCoordinates()[1];
        game.queryDestinationSquare(x1, y1, x2, y2);
        return;
    }

    // MODIFIES: this
    // sets up gui components of the chess app, including the board, pieces, material display,
    // and the toolbar (containing the new game, save, and restore buttons and the message Jlabel)
    public final void initializeGui() {
        createImages();
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);
        gui.add(materialDisplay, BorderLayout.LINE_END);
        Action newGameAction = new AbstractAction("New") {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupNewGame();
            }
        };
        tools.add(newGameAction);
        tools.add(savebutton = new SaveButton("Save", game));
        tools.add(restoreButton = new RestoreButton("Restore", game));
        tools.add(materialButton = new MaterialButton("Calculate Material", game, materialDisplay));
        tools.addSeparator();
        tools.addSeparator();
        tools.add(message);

        initalizeChessBoard();
        initializeSquares();
        fillBoard();
    }

    // MODIFIES: this
    // EFFECTS: fills chessboard with squares and add labels for row and column axes
    private void fillBoard() {
        chessBoard.add(new JLabel(""));
        for (int i = 0; i < 8; i++) {
            chessBoard.add(
                    new JLabel(COLS.substring(i, i + 1),
                            SwingConstants.CENTER));
        }
        // fill the black non-pawn piece row
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (j) {
                    case 0:
                        chessBoard.add(new JLabel("" + (9 - (i + 1)),
                                SwingConstants.CENTER));
                    default:
                        chessBoard.add(chessBoardSquareButtons[j][i]);
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes squares (which extend JButton) of chessboard by setting their background color
    // and setting their size to fit 64 x 64 pixel images
    private void initializeSquares() {
        Insets buttonMargin = new Insets(0, 0, 0, 0);
        for (int ii = 0; ii < chessBoardSquareButtons.length; ii++) {
            for (int jj = 0; jj < chessBoardSquareButtons[ii].length; jj++) {
                SquareButton b = new SquareButton(jj, ii, this);
                b.setMargin(buttonMargin);
                // our chess pieces are 64x64 px in size, so we'll
                // 'fill this in' using a transparent icon..
                ImageIcon icon = new ImageIcon(
                        new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
                b.setIcon(icon);
                if ((jj % 2 == 1 && ii % 2 == 1)
                        //) {
                        || (jj % 2 == 0 && ii % 2 == 0)) {
                    b.setBackground(Color.WHITE);
                } else {
                    b.setBackground(Color.BLACK);
                }
                chessBoardSquareButtons[jj][ii] = b;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: set up JPanel for chessboard and set up the background JPanel to fit chessboard in
    private void initalizeChessBoard() {
        chessBoard = new JPanel(new GridLayout(0, 9));
        chessBoard.setBorder(new CompoundBorder(
                new EmptyBorder(8, 8, 8, 8),
                new LineBorder(Color.BLACK)
        ));
        Color color = new Color(55, 211, 245);
        chessBoard.setBackground(color);
        JPanel boardConstrain = new JPanel(new GridBagLayout());
        boardConstrain.setBackground(color);
        boardConstrain.add(chessBoard);
        gui.add(boardConstrain);
    }

    // MODIFIES: this
    // EFFECTS: obtain images of each chess piece from internet and set their dimensions to be 64 x 64 pixels
    // throws exception if unsuccessful
    private final void createImages() {
        try {
            URL url = new URL("http://i.stack.imgur.com/memI0.png");
            BufferedImage bi = ImageIO.read(url);
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 6; j++) {
                    chessPieceImages[i][j] = bi.getSubimage(
                            j * 64, i * 64, 64, 64);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds piece icons to their positions at the start of the game
    private final void setupNewGame() {
        clearBoard();
        for (int i = 0; i < STARTING_ROW.length; i++) {
            chessBoardSquareButtons[i][0].setIcon(new ImageIcon(
                    chessPieceImages[BLACK][STARTING_ROW[i]]));

        }
        for (int i = 0; i < STARTING_ROW.length; i++) {
            chessBoardSquareButtons[i][1].setIcon(new ImageIcon(
                    chessPieceImages[BLACK][PAWN]));
        }
        for (int i = 0; i < STARTING_ROW.length; i++) {
            chessBoardSquareButtons[i][6].setIcon(new ImageIcon(
                    chessPieceImages[WHITE][PAWN]));
        }
        for (int i = 0; i < STARTING_ROW.length; i++) {
            chessBoardSquareButtons[i][7].setIcon(new ImageIcon(
                    chessPieceImages[WHITE][STARTING_ROW[i]]));
        }
    }

    // MODIFIES: this
    // EFFECTS: removes all piece icons from chessBoardSquares
    private void clearBoard() {
        for (int i = 0; i < chessBoardSquareButtons.length; i++) {
            for (int j = 0; j < chessBoardSquareButtons.length; j++) {
                chessBoardSquareButtons[i][j].setIcon(null);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: given a char representation of the saved board state, create the equivalent GUI representation
    public void restoreBoardState(char[][] savedBoard) {
        for (int i = 0; i < savedBoard.length; i++) {
            for (int j = 0; j < savedBoard.length; j++) {
                Image image = getIconFromChar(savedBoard[i][j]);
                if (image == null) {
                    continue;
                }
                chessBoardSquareButtons[i][j].setIcon(new ImageIcon(image));
            }
        }
    }

    //EFFECTS: given the character representation of a piece, returns the image representation of that piece
    private Image getIconFromChar(char c) {
        if (c == 'p') {
            return chessPieceImages[BLACK][PAWN];
        } else if (c == 'P') {
            return chessPieceImages[WHITE][PAWN];
        } else if (c == 'n') {
            return chessPieceImages[BLACK][KNIGHT];
        } else if (c == 'N') {
            return chessPieceImages[WHITE][KNIGHT];
        } else if (c == 'b') {
            return chessPieceImages[BLACK][BISHOP];
        } else if (c == 'B') {
            return chessPieceImages[WHITE][BISHOP];
        } else if (c == 'q') {
            return chessPieceImages[BLACK][QUEEN];
        } else if (c == 'Q') {
            return chessPieceImages[WHITE][QUEEN];
        } else if (c == 'k') {
            return chessPieceImages[BLACK][KING];
        } else if (c == 'K') {
            return chessPieceImages[WHITE][KING];
        } else {
            return getRook(c);
        }
    }

    //EFFECTS: given the character representation of a rook, returns the image representation of rook
    public Image getRook(char c) {
        if (c == 'r') {
            return chessPieceImages[BLACK][ROOK];
        } else if (c == 'R') {
            return chessPieceImages[WHITE][ROOK];
        } else {
            return null;
        }
    }

    //EFFECTS: displays each player's captured pieces in display;
    public void displayMaterial() {
        materialDisplay.getMaterial();
    }
}

