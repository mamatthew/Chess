package persistence.test;

import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

//The following code is based on following class in the sample project
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/ :
// src/main/test/persistence/JsonReaderTest.java

public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/file/does/not/exist.json");
        try {
            char[][] board = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            //expected
        }
    }

    @Test
    void testReaderDefaultChessBoard() {
        JsonReader reader = new JsonReader("./data/testWriterDefaultBoard.json");
        try {
            char[][] saved = reader.read();
            compareBoards(board,saved);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
