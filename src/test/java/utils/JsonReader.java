package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.TestData;

import java.io.IOException;
import java.io.InputStream;

public class JsonReader {

    private static final String FILE_NAME = "data/Data.json";

    public static TestData getData(){
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = JsonReader.class.getClassLoader().getResourceAsStream(FILE_NAME)){
            if (is == null){
                throw new RuntimeException("File JSON not found: " + FILE_NAME);
            }
            return mapper.readValue(is, TestData.class);
        } catch (IOException e) {
            throw new RuntimeException("Error read file JSON: " + e.getMessage());
        }
    }
}
