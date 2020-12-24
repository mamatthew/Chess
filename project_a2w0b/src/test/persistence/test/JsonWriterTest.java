package persistence.test;

import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

//The following code is based on following class in the sample project
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/ :
// src/main/test/persistence/JsonWriterTest.java

public class JsonWriterTest extends JsonTest{


    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterDefaultBoard() {
        try {
            String dir = "./data/testWriterDefaultBoard.json";
            JsonWriter writer = new JsonWriter(dir);

            writer.open();
            writer.write(board);
            writer.close();
            JsonReader reader = new JsonReader(dir);
            char[][] saved = reader.read();
            compareBoards(board, saved);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
