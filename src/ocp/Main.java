package ocp;

public class Main {
    public static void main(String[] args) {
        NotificationService service = new NotificationService();

        service.sendNotification(new EmailNotification(), "Hello via Email!");
        service.sendNotification(new SMSNotification(), "Hello via SMS!");
        service.sendNotification(new PushNotification(), "Hello via Push ocp.Notification!");

        // Example of easily adding a new notification type (no change to ocp.NotificationService needed)
        Notification faxNotification = new Notification() {
            public void send(String message) {
                System.out.println("Sending Fax: " + message);
            }
        };
        service.sendNotification(faxNotification, "Hello via Fax!");
    }
}