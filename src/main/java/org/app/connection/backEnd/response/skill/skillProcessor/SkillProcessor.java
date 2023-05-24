package org.app.connection.backEnd.response.skill.skillProcessor;

import org.app.connection.backEnd.response.skill.skillData.SkillData;
import org.app.connection.backEnd.response.skill.skillProcessor.process.*;

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
        this.processQuery(data.getQuery());
    }

    private void unifyData(List<String[]> data){
        if(data == null) return;
        this.unifiedData.addAll(data);
    }

    private void processTemplate(List<String> dataTemplate){
        if(dataTemplate == null) return;
        this.formatTemplate = new FormatTemplate(dataTemplate);
        unifyData(formatTemplate.getData());
    }

    private void processCFG(List<String> dataCFG){
        if(dataCFG == null) return;
        this.formatCFG = new FormatCFG(dataCFG);
        unifyData(formatCFG.getData());
    }

    private void processQuery(List<String> dataQuery){
        if(dataQuery == null) return;
        this.formatQuery = new FormatQuery(dataQuery);
        unifyData(formatQuery.getData());
    }

    public List<String[]> getUnifiedData() {
        return unifiedData;
    }
}
