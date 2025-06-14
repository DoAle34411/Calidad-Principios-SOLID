package srp;

public class UserManager {
    private EmailValidator emailValidator;
    private PasswordValidator passwordValidator;
    private UserRepository userRepository;
    private EmailService emailService;

    public UserManager(EmailValidator emailValidator, PasswordValidator passwordValidator,
                       UserRepository userRepository, EmailService emailService) {
        this.emailValidator = emailValidator;
        this.passwordValidator = passwordValidator;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public void addUser(String email, String password) {
        if (emailValidator.isValid(email) && passwordValidator.isValid(password)) {
            userRepository.save(email, password);
            emailService.sendWelcomeEmail(email);
        } else {
            System.out.println("Invalid email or password. User not added.");
        }
    }
}
