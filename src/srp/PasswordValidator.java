package srp;

public class PasswordValidator {
    public boolean isValid(String password) {
        return password.length() >= 8;
    }
}
