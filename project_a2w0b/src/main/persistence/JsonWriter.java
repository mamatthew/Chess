package persistence;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

//The following code is based on following class in the sample project
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/ :
// src/main/persistence/JsonWriter.java

public class JsonWriter {

    private PrintWriter writer;
    private String destination;

    // EFFECTS: sets the destination of the file to be written
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: create a new file writer
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: converts a character matrix to a json object and saves it to file
    public void write(char[][] board) {
        JSONObject json = toJson(board);
        saveToFile(json.toString());
    }

    // EFFECTS: converts a character matrix to a json object
    public JSONObject toJson(char[][] board) {
        JSONObject json = new JSONObject();
        json.put("squares", squaresToJson(board));
        return json;
    }

    // EFFECTS: converts a character matrix to a json array
    private JSONArray squaresToJson(char[][] board) {
        JSONArray boardMatrixArray = new JSONArray();

        for (int i = 0; i < 8; i++) {
            JSONObject rowObject = new JSONObject();
            JSONArray array = new JSONArray();
            rowObject.put(String.valueOf(i), array);
            for (int j = 0; j < 8; j++) {
                array.put(board[j][i]);
            }
            boardMatrixArray.put(rowObject);

        }
        return boardMatrixArray;
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
