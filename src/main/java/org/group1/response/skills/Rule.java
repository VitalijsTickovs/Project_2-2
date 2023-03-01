package org.group1.response.skills;

import org.group1.processor.PreProcessor;

import java.util.List;


public class Rule {

    // Slot with variable
    //      e.g. {Monday, 9}
    public List<String> pairs;

    // The corresponding action
    //      e.g {We start the week with math}
    public Action action;

    // TODO: add the preprocessing here.. (instead of in skill)
    public Rule(List<String> pairs, Action action) throws Exception{
        this.pairs = PreProcessor.preprocess(pairs);
        this.action = action;
    }


    }
