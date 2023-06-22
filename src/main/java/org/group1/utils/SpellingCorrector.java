package org.group1.utils;

import org.languagetool.JLanguageTool;
import org.languagetool.language.*;
import org.languagetool.rules.RuleMatch;

import java.io.IOException;
import java.util.List;


public class SpellingCorrector {

    private JLanguageTool tool = new JLanguageTool(new BritishEnglish());
    public SpellingCorrector(Language language) {
        switch (language){
            case BRITISH -> this.tool = new JLanguageTool(new BritishEnglish());
            case AMERICAN -> this.tool = new JLanguageTool(new AmericanEnglish());
            case NEW_ZELAND ->  this.tool = new JLanguageTool(new NewZealandEnglish());
            case CANADIAN -> this.tool = new JLanguageTool(new CanadianEnglish());
            case AUSTRALIAN -> this.tool = new JLanguageTool(new AustralianEnglish());
        }
    }
    public SpellingCorrector() {this(Language.BRITISH);}

    public String correctSentence(String sentence) throws IOException{
        List<RuleMatch> matches = tool.check(sentence);
        String output = sentence;

        for (RuleMatch match : matches) {
            String incorrectWord = "";
            for (int i = match.getFromPos(); i < match.getToPos(); i++) {
                incorrectWord = incorrectWord.concat(String.valueOf(sentence.charAt(i)));
            }
            System.out.println("Spelling mistake in " + incorrectWord);
            List<String> replacements = match.getSuggestedReplacements();
            output = output.replace(incorrectWord, replacements.get(0));
        }

        return output;
    }

    public JLanguageTool getTool() {
        return tool;
    }
}

