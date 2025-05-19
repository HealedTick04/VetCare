package VetCare.Utils;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class TelephoneValidator {

    private static final String TELEPHONE_PATTERN = "^(\\+52)?[-\\s]?\\d{2}[-\\s]?\\d{4}[-\\s]?\\d{4}$";
    private static final Pattern pattern = Pattern.compile(TELEPHONE_PATTERN);

    public static boolean isValid(String phoneNumber) {
        if (phoneNumber == null) 
            return false;
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
}
