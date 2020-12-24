package persistence;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


//The following code is based on following class in the sample project
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/ :
// src/main/persistence/JsonReader.java

public class JsonReader {
    private String source;

    // EFFECTS: sets destination of the file to be read
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads chess board from .json file and returns a character matrix representation
    // throws IOException if an error occurs reading data from file
    public char[][] read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBoard(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }


    // EFFECTS: parses character matrix from JSON object and returns it
    private char[][] parseBoard(JSONObject jsonObject) {
        char[][] board = new char[8][8];
        addPieces(board, jsonObject);
        return board;
    }

    // MODIFIES: board
    // EFFECTS: parses character matrix board from JSON object
    private void addPieces(char[][] board, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("squares");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject row = jsonArray.getJSONObject(i);
            addRow(board, i, row);
        }
    }

    // MODIFIES: board
    // EFFECTS: parses row of character matrix from JSON Array
    private void addRow(char[][] board, int i, JSONObject row) {
        for (int j = 0; j < 8; j++) {
            JSONArray array = (JSONArray) row.get(Integer.toString(i));
            board[j][i] = Character.toChars(array.getInt(j))[0];
        }
    }
}
