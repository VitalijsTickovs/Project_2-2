package org.app.connection.backEnd.response.skill.skillProcessor;

import org.app.connection.backEnd.response.skill.skillData.SkillData;
import org.app.connection.backEnd.response.skill.skillProcessor.format.*;

import java.util.ArrayList;
import java.util.List;


public class SkillProcessor {

    private FormatCFG formatCFG;
    private FormatQuery formatQuery;
    private FormatTemplate formatTemplate;
    private List<String[]> unifiedData;


    public SkillProcessor(SkillData data){
        this.unifiedData = new ArrayList<>();
        this.processTemplate(data.getTemplate());
        this.processCFG(data.getCfg());
        this.processQuery(data.getCfg());
    }

    private void unifyData(List<String[]> data){
        this.unifiedData.addAll(data);
    }

    private void processTemplate(List<String> dataTemplate){
        this.formatTemplate = new FormatTemplate(dataTemplate);
        unifyData(formatTemplate.getData());
    }

    private void processCFG(List<String> dataCFG){
        this.formatCFG = new FormatCFG(dataCFG);
        unifyData(formatCFG.getData());
    }

    private void processQuery(List<String> dataQuery){
        this.formatQuery = new FormatQuery(dataQuery);
        unifyData(formatCFG.getData());
    }

    public List<String[]> getUnifiedData() {
        return unifiedData;
    }
}
