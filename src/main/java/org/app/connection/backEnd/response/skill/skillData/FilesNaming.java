package org.app.connection.backEnd.response.skill.skillData;

public enum FilesNaming {
    TEMPLATE("Rule_", ".txt", "/src/main/java/org/app/connection/backEnd/response/skill/skillData/templateSkills"),
    CFG("CFG_", ".txt", "/src/main/java/org/app/connection/backEnd/response/skill/skillData/cfgSkills"),
    QUERY("Query_", ".txt", "/src/main/java/org/app/connection/backEnd/response/skill/skillData/querySkills");

    String name, extension, directory;

     FilesNaming(String name, String extension, String directory){
        this.directory = directory;
        this.name = name;
        this.extension = extension;
    }
}
