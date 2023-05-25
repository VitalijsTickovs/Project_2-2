package org.app.connection.backEnd.response.skill.skillProcessor.process;

import java.util.ArrayList;
import java.util.*;

public class FormatCFG {

    private List<String[]> REAL_DATA;

    public FormatCFG(List<String> data) {
        REAL_DATA = new ArrayList<>();
        processAll(data);
    }
    public void processAll(List<String> data) {
        org.app.connection.backEnd.response.skill.skillProcessor.process.CFG.FormatCFG formatCfg = new org.app.connection.backEnd.response.skill.skillProcessor.process.CFG.FormatCFG(data);
        for(String component: data){
            String[] text = component.split("\n");
            List<String> input = new ArrayList<>();
            for (String row: text) {
                input.add(row);
            }

            formatCfg = new org.app.connection.backEnd.response.skill.skillProcessor.process.CFG.FormatCFG(input);
            REAL_DATA.addAll(formatCfg.getRealData());
        }
        REAL_DATA = formatCfg.getRealData();
    }

    public List<String[]> getData() {
        return REAL_DATA;
    }

}

