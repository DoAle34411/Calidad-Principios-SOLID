package isp;

public class Main {
    public static void main(String[] args) {
        Switchable phone = new Phone();
        Switchable camera = new DisposableCamera();

        phone.turnOn();
        phone.turnOff();


        if (phone instanceof Rechargeable) {
            ((Rechargeable) phone).charge();
        }

        camera.turnOn();
        camera.turnOff();

        if (camera instanceof Rechargeable) {
            ((Rechargeable) camera).charge();
        }
    }
}

