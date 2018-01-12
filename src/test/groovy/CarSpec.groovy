import spock.lang.*
import spockdemo.*

class CarSpec extends Specification{

    // 1. Basic test







    // 2. Data driven
    def "The car can't be driven more than the amount of gas in the car"() {
        given:
        Car car = new Car();
        car.addGas(5)

        when:
        car.drive(10)

        then:
        car.getTotalDistanceDriven() == 5
    }





    // Mock
    def "When driver won't drive"() {
        given:
        Driver driver = Mock()
        driver.isWillingToDrive(_) >> false

        //driver.isWillingToDrive(_) >> {int distance -> distance > 2 }

        Car car = new Car(driver);

        when:
        car.addGas(15)
        car.drive(10)

        then:
        car.getTotalDistanceDriven() == 0
    }


    // interactions
    def "Number of requests to isWillingToDrive incremented after driving"() {
        given:
        Driver driver = Spy();
        Car car = new Car(driver)

        when:
        car.addGas(5)
        car.drive(1)
        car.drive(1)

        then:
        2 * driver.isWillingToDrive(_)

        driver.distanceDriven == 2
    }

    // expect
    @Unroll
    def "Car reports when it is able to drive the full distance"() {
        given:
        Car car = new Car()
        car.addGas(gas)

        expect:
        didDriveFullAmount == car.drive(distance)

        where:
        gas  | distance || didDriveFullAmount
        5    | 5        || true
        5    | 10       || false

    }

    // exception
    def "expect exception to be thrown"() {
        given:
        Driver driver = Mock()
        Car car = new Car(driver)

        when:
        driver.isWillingToDrive(_) >> { throw new InternalError("ouch") }
        car.drive(1)

        then:
        thrown(InternalError)
    }

    // 'with' checks 2 values
    @Unroll
    def "check distance and gas remaining with #gas gas and #distance distance"() {
        given:

        @Subject
        Car car = new Car()

        when:
        car.addGas(gas)
        car.drive(distance)

        then:
        with(car) {
            totalDistanceDriven == expectedTotalDistance
            gasRemaining == expectedGasRemaining
        }

        where:
        gas  | distance || expectedTotalDistance | expectedGasRemaining
        5    |  3       ||   3                   |  2
        5    | 10       ||   5                   | 0
    }
}
