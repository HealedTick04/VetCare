/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VetCare.Utils;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


/**
 *
 * @author dary_
 */
public class PasswordValidator {
    private static final String PASSWORD_PATTERN =
        "^(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=.*\\d)(?=\\S+$).{8,}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public static boolean isValid(final String password) {
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}