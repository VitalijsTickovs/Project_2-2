package org.group1.back_end.response.skills;

import org.group1.back_end.response.skills.dataframe.Cell;
import org.group1.back_end.response.skills.dataframe.DataFrame;
import org.group1.back_end.response.skills.dataframe.Rows;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SkillData {

    String question;
    DataFrame slots;
    DataFrame actions;

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setSlots(DataFrame slots) {
        this.slots = slots;
        constructActionFrame();
    }

    public void setActions(DataFrame actions) {
        this.actions = actions;
    }

    public List<String> getSlotNames(){
        return slots.getColumnNames();
    }


    public void insertAction(List<String> _slots, String action){

        //slots; <type> slot, <type> slot, <type> slot
        System.out.println("inserting action: ");
        Rows row = emptyActionRow();
        List<String> types = slots.getColumnNames();
        for(String slot : _slots){
            Pattern pattern = Pattern.compile("^(<[^>]+>)\\s+(\\w+)$");
            Matcher matcher = pattern.matcher(slot);
            String type, value;
            type = value = "";
            if (matcher.find()) {
                type = matcher.group(1);
                value = matcher.group(2);
            } else {
                System.out.println("The input string does not match the pattern.");
            }
            int index = types.indexOf(type); //todo: this will -1
            row.getCells().get(index).setValue(value);
        }
        row.getCells().get(row.size()-1).setValue(action);
        actions.insert(row);

    }

    private void constructActionFrame(){
        List<String> actionNames = new ArrayList<>(slots.getColumnNames());
        actionNames.add("Action");
        actions = new DataFrame(actionNames);
    }


    private Rows emptyActionRow(){
        List<Cell> cells = new ArrayList<>();
        for(int i = 0; i < actions.getColumnNames().size(); i++){
            cells.add(new Cell(""));
        }
        return new Rows(cells);
    }



    @Override
    public String toString(){
        String toret = "Question: " + question + "\n";
        toret += slots + "\n";
        toret += actions + "\n";
        return toret;
    }
}
