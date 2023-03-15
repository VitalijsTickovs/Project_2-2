package org.group1.front_end.GUI;

public class ErrorHandling {
    public boolean stringLengthError(String string){
        System.out.println(string);
        System.out.println(string.length());
     if(string.length()>0){
         return true;
     }return false;
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
