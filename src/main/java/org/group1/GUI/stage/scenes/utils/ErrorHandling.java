package org.group1.GUI.stage.scenes.utils;

public class ErrorHandling {
    public static boolean stringLengthError(String string){
        return string.length() > 0;
    }
    //checks if a string has numbers
    public boolean numberError( String check ){
        char chr[] = check.toCharArray();
        for (char c : chr){
            if (!Character.isDigit(c)){
                return true;
            }
        }
        return false;
    }
}
