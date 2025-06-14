package srp;

import java.util.regex.Pattern;

public class EmailValidator {
    public boolean isValid(String email) {
        return Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email);
    }
}
