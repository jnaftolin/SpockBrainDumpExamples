package spockdemo;

public class Main {
    public static void main(String args[]) {
        Car car = new Car();
        car.addGas(10);
        car.drive(6);
        System.out.println("Car has driven a distance of " + car.getTotalDistanceDriven());
    }
}
