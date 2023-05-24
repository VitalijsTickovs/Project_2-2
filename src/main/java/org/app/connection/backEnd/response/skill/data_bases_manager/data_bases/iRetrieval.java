package org.app.connection.backEnd.response.skill.data_bases_manager.data_bases;

import java.util.*;

public interface iRetrieval{

    List<String> sortMapping(Map<String, String> dataBase);
    String getValue();

}
