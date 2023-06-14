package org.group1.back_end.response.skills.NLU;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSONConverter {
    List<Input> inputsList;

    public JSONConverter(){
        inputsList = new ArrayList<>();
    }

    public void addInput(Input input){
        inputsList.add(input);
    }
    public void makeJSON(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(inputsList);

        // Write JSON to a file
        try (FileWriter fileWriter = new FileWriter("NLU.json")) {
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
