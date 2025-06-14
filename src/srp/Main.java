package srp;

public class Main {
    public static void main(String[] args) {
        // Crear instancias de clases de soporte
        EmailValidator emailValidator = new EmailValidator();
        PasswordValidator passwordValidator = new PasswordValidator();
        UserRepository userRepository = new UserRepository();
        EmailService emailService = new EmailService();

        // Crear a user manager con sus nuevas dependencias
        UserManager userManager = new UserManager(emailValidator, passwordValidator, userRepository, emailService);

        // Agregando usuarios validos e invalidos
        userManager.addUser("example@domain.com", "password123");
        userManager.addUser("invalid-email", "1234");
    }
}
