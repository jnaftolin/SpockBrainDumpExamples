package spockdemo;

public class Car {

    private int totalDistanceDriven = 0;
    private int gasRemaining = 0;
    private FuelEfficiencyLogic fuelEfficiencyLogic;

    public Car(FuelEfficiencyLogic fuelEfficiencyLogic) {
        this.fuelEfficiencyLogic = fuelEfficiencyLogic;
    }

    public Car() {
        this(new FuelEfficiencyLogic());
    }

    // Drives the car up to the distance specified, depending on how much fuel is remaining.
    // Returns true if we had enough gas to drive the full distance specified,
    // or false if there wasn't enough gas to drive the full distance.
    public boolean drive(int requestedDistance) {

        int distanceAllowed = fuelEfficiencyLogic.getRange(gasRemaining);

        int distanceDriven = (requestedDistance <= distanceAllowed) ? requestedDistance : distanceAllowed;

        totalDistanceDriven += distanceDriven;
        gasRemaining -= distanceDriven;
        return (distanceDriven == requestedDistance);
    }

    public void addGas(int gasAmount) {
        gasRemaining += gasAmount;
    }

    public int getGasRemaining() {
        return gasRemaining;
    }

    public int getTotalDistanceDriven() {
        return totalDistanceDriven;
    }
}