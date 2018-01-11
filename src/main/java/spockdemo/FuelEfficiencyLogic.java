package spockdemo;

public class FuelEfficiencyLogic {

    private int requests;

    public int getRequests() {
        return requests;
    }

    // returns the distance that can be driven given a specified amount of gas
    public int getRange(int gas) {
        requests++;

        // one unit of gas returns one unit of distance
        return gas;
    }
}