package org.group1.back_end.response.skills.NLU;

import org.group1.back_end.textprocessing.ComplexProcess;
import org.group1.back_end.textprocessing.SimpleProcess;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Input {
    private List<Entry> entries;
    private List<String> examples;

    public Input() {
        this.entries = new ArrayList<>();
        examples = new ArrayList<>();
    }

    public void addEntry(String[][] data, String query) {
        if(data.length>0) {
            //For intent
            String copyQuery = query;
            copyQuery = copyQuery.replaceAll("<.*?>", "");
            Set<String> intentSet = ComplexProcess.process(SimpleProcess.process(copyQuery));
            String intent = String.join("_", intentSet);

            //What values are used for query
            List<Entity> entities = new ArrayList<>();
            for (String[] slot : data) {
                int start = query.indexOf(slot[1]);
                int end = start + slot[0].length();
                entities.add(new Entity(start, end, slot[0], slot[1]));

                query = query.replace(slot[1], slot[0]);
            }
            Entry entry = new Entry(query, intent, entities);
            entries.add(entry);
        }
    }

    public void addExample(String query){

    }

    public void addStory(String output){

    }

    public void setEntries(List<Entry> common_examples) {
        this.entries = common_examples;
    }
}
