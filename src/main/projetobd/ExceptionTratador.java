/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.projetobd;

/**
 *
 * @author Ariel
 */
public class ExceptionTratador {
    public static String exceptionToString(String ex){
        switch(ex){
            case "ST":
                return "Usuário sem tipo definido";
        }
        return "";
    }
}
