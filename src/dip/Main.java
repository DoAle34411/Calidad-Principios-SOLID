package dip;

public class Main {
    public static void main(String[] args) {
        PaymentMethod creditCard = new CreditCardPayment();
        PaymentMethod paypal = new PayPalPayment();

        PaymentProcessor creditCardProcessor = new PaymentProcessor(creditCard);
        PaymentProcessor paypalProcessor = new PaymentProcessor(paypal);

        creditCardProcessor.makePayment(150.0);
        paypalProcessor.makePayment(75.0);
    }
}