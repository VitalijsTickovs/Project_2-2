package org.group1.response.skills;

import java.util.List;


public class Rule {

    // Slot with variable
    //      e.g. {Monday, 9}
    public List<String> pairs;

    // The corresponding action
    //      e.g {We start the week with math}
    public Action action;

    // TODO: add the preprocessing here.. (instead of in skill)
    public Rule(List<String> pairs, Action action) {
        this.pairs = pairs;
        this.action = action;
    }


    }
