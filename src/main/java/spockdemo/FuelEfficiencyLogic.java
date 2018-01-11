package spockdemo;

public class FuelEfficiencyLogic {

    private float gasToRangeRatio = 1;
    private int rangeRequestsCount;

    public float getGasConsumed(float distance) {
        return distance / gasToRangeRatio;
    }

    // returns the distance that can be driven given a specified amount of gas
    public float getRange(int gas) {
        rangeRequestsCount++;
        return gas * gasToRangeRatio;
    }


    public int getRangeRequestsCount() {
        return rangeRequestsCount;
    }
}