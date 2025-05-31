/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VetCare.Utils;

/**
 *
 * @author gabri
 */
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class StringValidator {

    // Acepta letras (mayúsculas y minúsculas) y espacios
    private static final String WORD_PATTERN = "^[\\p{L} ]+$";
    private static final Pattern pattern = Pattern.compile(WORD_PATTERN);

    public static boolean isValid(String input) {
        if (input == null) 
            return false;
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

}

