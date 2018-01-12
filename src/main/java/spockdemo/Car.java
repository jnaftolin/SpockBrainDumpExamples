package spockdemo;

public class Car {

    //
    // Constructors
    //

    public Car(Driver driver) {

        this.driver = driver;
    }

    public Car() {

        this(new Driver());
    }


    //
    // Methods
    //

    public void addGas(int gasAmount) {
        gasRemaining += gasAmount;
    }

    public int getGasRemaining() {
        return gasRemaining;
    }

    public int getTotalDistanceDriven() {
        return totalDistanceDriven;
    }


    // Attemps to drive the car the distance specified.
    // If the driver is unwilling to drill the amount requested, the car won't move.
    // Otherwise,
    //  If there's enough gas, it will go the requested amount.
    //  If there's not enough gas, it will go as far as it can.
    // Returns true if the car moved the requested amount, false otherwise.
    public boolean drive(int requestedDistance) {

        if (!driver.isWillingToDrive(requestedDistance)) {
            return false;
        }

        // assume 1 unit of gas provides 1 unit of distance
        int distanceToDrive = Math.min(requestedDistance, gasRemaining);

        // update state
        driver.incrementDistanceDriven(distanceToDrive);
        totalDistanceDriven += distanceToDrive;
        gasRemaining -= distanceToDrive;

        // return true if we were able to drive the requested distance
        return (distanceToDrive == requestedDistance);
    }

    //
    // Member variables
    //

    private int totalDistanceDriven = 0;
    private int gasRemaining = 0;
    private Driver driver;
}