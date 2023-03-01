package org.group1.reponse;

public class RegularExpresions {


    // {n} -> repetir n veces
    public static boolean validarEntero(String numero){
        return numero.matches("^-?[0-9]*$");
    }

    // {n} -> repetir n veces
    public static boolean validaDNI(String dni){
        return dni.matches("^[0-9]{8}[A-Z]$");
    }

    // + -> una o mas veces
    // ? -> 0 o 1 vez (puede estar o puede no estar)
    // * -> 0 o más veces
    public static boolean validaNombre(String nombre){
        return nombre.matches("^([A-Z]{1}[a-z]+[ ]?){1,2}$");
    }
}
