package persistence.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    protected char[][] board = new char[][]
       {{'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'},
        {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
        {'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
        {'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
        {'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
        {'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
        {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'},
        {'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r'}};

    public void compareBoards(char[][] board1, char[][] board2) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                assertEquals(board1[i][j], board2[i][j]);
            }
        }
    }
}
