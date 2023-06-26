package org.group1.back_end.response.skills.NLU;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JSONConverter {
    List<Input> inputsList;

    public JSONConverter(){
        inputsList = new ArrayList<>();
    }

    public void addInput(Input input){
        inputsList.add(input);
    }
    public static void makeJSON(List<HashMap<String, List<String>>> intents){
        StringBuilder yamlBuilder = new StringBuilder();
        yamlBuilder.append("version: \"3.1\"\n");
        yamlBuilder.append("\n").append("nlu:\n");
        for (HashMap<String, List<String>> hashMap : intents) {
            for (String key : hashMap.keySet()) {
                yamlBuilder.append("- intents: ").append(key).append("\n");
                yamlBuilder.append("  examples: |").append("\n");

                List<String> values = hashMap.get(key);
                for (String value : values) {
                    yamlBuilder.append("    - ").append(value).append("\n");
                }

                yamlBuilder.append("\n");
            }
        }

        try (FileWriter fileWriter = new FileWriter("src/main/resources/RASATrainingData/nlu.yaml")) {
            fileWriter.write(yamlBuilder.toString());
        } catch (IOException e) {
            System.out.println("An error occurred while saving the YAML to a file: " + e.getMessage());
        }
    }
}
