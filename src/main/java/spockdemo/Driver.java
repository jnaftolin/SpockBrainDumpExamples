package spockdemo;

public class Driver {

    private final int MAX_TOTAL_DISTANCE = 20;

    public void incrementDistanceDriven(int distance) {
        totalDistanceDriven += distance;
    }

    public int getDistanceDriven() {
        return totalDistanceDriven;
    };

    public boolean isWillingToDrive(int distance) {
        return (totalDistanceDriven + distance) < MAX_TOTAL_DISTANCE;
    }

    //
    // Member variables
    //
    private int totalDistanceDriven = 0;
}
