package org.group1.back_end.response.skills.NLU;

import org.group1.back_end.textprocessing.ComplexProcess;
import org.group1.back_end.textprocessing.SimpleProcess;
import org.group1.back_end.utilities.strings.RegexUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class IntentGenerator {
    HashMap<String, List<String>> intents;
    public IntentGenerator(){
        intents = new HashMap<>();
    }

    public void addIntent(String[][] data, String query){
        String copyQuery = query;
        copyQuery = copyQuery.replaceAll("<.*?>", "");
        Set<String> intentSet = ComplexProcess.process(SimpleProcess.process(copyQuery));
        String intentKey = String.join("_", intentSet);
        for(String[] slot: data){
            intentKey += "_"+slot[1];
        }

        for (String[] slot : data) {
            String new_slot = "[" + slot[0] + "](" + slot[1].substring(1,slot[1].length()-1) + ")";
            query = query.replace(slot[1], new_slot);
        }
        query = RegexUtilities.replaceRegexAll(query, "<.*?>", "");


        if(!intents.containsKey(intentKey)){
            List<String> possibleEntries = new ArrayList<>();
            possibleEntries.add(query);
            intents.put(intentKey, possibleEntries);

        }else{
            List<String> possibleEntries = intents.get(intentKey);
            possibleEntries.add(query);
        }
    }

    public HashMap<String, List<String>> getIntents() {
        return intents;
    }
}
