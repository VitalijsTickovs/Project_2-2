package org.app.connection.backEnd.response.skill.skillData;

import java.util.ArrayList;
import java.util.List;

public class SkillData {
    
    private List<String> template = new ArrayList<>();
    private List<String> cfg = new ArrayList<>();
    private List<String> query = new ArrayList<>();
    private SkillFileService fileReader;
    
    public SkillData(){
        try {
            fileReader = new SkillFileService();

            fileReader.setFilesName(FilesNaming.TEMPLATE);
            template.addAll(fileReader.readAll());

            fileReader.setFilesName(FilesNaming.CFG);
            cfg.addAll(fileReader.readAll());

            fileReader.setFilesName(FilesNaming.QUERY);
            query.addAll(fileReader.readAll());

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setTemplate(List<String> template) {
        this.template = template;
    }

    public void setCfg(List<String> cfg) {
        this.cfg = cfg;
    }

    public void setQuery(List<String> query) {
        this.query = query;
    }

    public List<String> getTemplate() {
        return template;
    }

    public List<String> getCfg() {
        return cfg;
    }

    public List<String> getQuery() {
        return query;
    }
}
