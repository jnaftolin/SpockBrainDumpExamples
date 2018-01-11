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

    public void addGas(int gasAmount) {
        gasRemaining += gasAmount;
    }

    public int getGasRemaining() {
        return gasRemaining;
    }

    public int getTotalDistanceDriven() {
        return totalDistanceDriven;
    }

    // Drives the car up to the distance specified, depending on how much fuel is remaining.
    // Returns true if we had enough gas to drive the full distance specified,
    // or false if there wasn't enough gas to drive the full distance.
    public boolean drive(int requestedDistance) {

        float maxDistance = fuelEfficiencyLogic.getRange(gasRemaining);

        float distanceDriven = 0;

        if (requestedDistance <= maxDistance) {
            distanceDriven = requestedDistance;
        } else {
            distanceDriven = maxDistance;
        }

        totalDistanceDriven += distanceDriven;
        gasRemaining -= fuelEfficiencyLogic.getGasConsumed(distanceDriven);

        return (distanceDriven == requestedDistance);
    }
}