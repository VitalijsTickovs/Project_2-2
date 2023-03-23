package org.group1.back_end.textprocessing;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleProcess {


    static final List<String> stopwords = Arrays.asList(
                                                        "aboard", "about", "above", "across", "after", "against", "along", "amid", "among",
                                                        "around", "as", "at", "before", "behind", "below", "beneath", "beside", "between",
                                                        "beyond", "but", "by", "concerning", "considering", "despite", "down", "during", "except",
                                                        "for", "from", "in", "inside", "into", "like", "near", "of", "off", "on", "onto", "out",
                                                        "outside", "over", "past", "regarding", "round", "since", "through", "throughout", "to",
                                                        "toward", "under", "underneath", "unlike", "until", "up", "upon", "with", "within", "without"
                                                        );

    public static List<String> process(String text){
        text = temporalAlgorithm(text);
        text = Pattern.compile("[^-<>\\w]+").matcher(text).replaceAll(" ").trim();
        List<String> temp = new ArrayList<>();
        StringTokenizer tokens = new StringTokenizer(text, " ");

        while (tokens.hasMoreTokens()){
            temp.add(tokens.nextToken().toLowerCase());
        }
        return temp;
    }

    public static String temporalAlgorithm(String text){
        text = text.toLowerCase().trim();
        Pattern patron = Pattern.compile("(today|tomorrow|yesterday|in (\\d+) days|(\\d+) days ago|next (\\d+) days)");
        Matcher matcher = patron.matcher(text);

        LocalDate fechaActual = LocalDate.now();
        DayOfWeek day = fechaActual.getDayOfWeek();

        while (matcher.find()) {
            String expresion = matcher.group(1);
            String valor = matcher.group(2);

            if (expresion.equals("today")) {

            } else if (expresion.equals("tomorrow")) {
                day = day.plus(1);
            } else if (expresion.equals("yesterday")) {
                day = day.minus(1);
            } else if (expresion.matches("in \\d+ days")) {
                int dias = Integer.parseInt(valor);
                day = fechaActual.plusDays(dias).getDayOfWeek();
            } else if (expresion.matches("\\d+ days ago")) {
                int dias = Integer.parseInt(valor);
                day = fechaActual.minusDays(dias).getDayOfWeek();
            } else if (expresion.matches("next \\d+ days")) {
                int dias = Integer.parseInt(valor);
                day = fechaActual.plusDays(dias).getDayOfWeek();
            }

            text = text.replaceAll(expresion, day.toString().toLowerCase());
        }

        return text;
    }

    public static void softRemoveStopWords(List<String> toClean){
       toClean.removeIf(stopwords::contains);
    }


    public static List<String> processForVocabulary(String text){

        text = temporalAlgorithm(text);
        text = Pattern.compile("[^\\w]+").matcher(text).replaceAll(" ").trim();
        List<String> temp = new ArrayList<>();
        StringTokenizer tokens = new StringTokenizer(text, " ");

        while (tokens.hasMoreTokens()){
            temp.add(tokens.nextToken().toLowerCase());
        }
        return temp;
    }

}
