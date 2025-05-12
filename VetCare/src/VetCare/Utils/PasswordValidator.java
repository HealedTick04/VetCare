/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VetCare.Utils;

/**
 *
 * @author dary_
 */
public class PasswordValidator {
    public static boolean validarContraseña(String contraseña) {
        if (contraseña.length() < 8) {
            return false; // Mínimo 8 caracteres
        }

        boolean tieneMayuscula = false;
        boolean tieneMinuscula = false;
        boolean tieneNumero = false;
        boolean tieneEspecial = false;

        // Puedes personalizar esta lista con los caracteres especiales que tú quieras
        String caracteresEspeciales = "@#$%&*!?";

        for (char c : contraseña.toCharArray()) {
            if (Character.isUpperCase(c)) tieneMayuscula = true;
            else if (Character.isLowerCase(c)) tieneMinuscula = true;
            else if (Character.isDigit(c)) tieneNumero = true;
            else if (caracteresEspeciales.indexOf(c) != -1) tieneEspecial = true;
        }

        return tieneMayuscula && tieneMinuscula && tieneNumero && tieneEspecial;
    }
}
