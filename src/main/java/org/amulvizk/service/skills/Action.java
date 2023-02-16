package org.amulvizk.service.skills;

import java.util.ArrayList;
import java.util.List;

public class Action implements iDataStore{

    List<String> action = new ArrayList<>();

    public Action() {}

    @Override
    public void add(String text) {
        action.add(text);
    }


}
