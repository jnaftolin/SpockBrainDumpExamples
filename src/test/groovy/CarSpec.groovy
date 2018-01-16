import spock.lang.*
import spockdemo.*

class CarSpec extends Specification{

    // 1. Basic test
    def "The car accepts gas"() {
        given:
        def car = new Car()

        when:
        car.addGas(5)

        then:
        car.gasRemaining == 5

        when:
        car.addGas(6)

        then:
        car.gasRemaining(11)
    }


    // 2. Data driven
    @Unroll
    def "The car can't be driven more than #distance with #gas of gas in the car"() {
        given:
        Car car = new Car()
        car.addGas(gas)

        when:
        car.drive(distance)

        then:
        car.getTotalDistanceDriven() == expectedDistance

        where:
        gas |   distance    |   expectedDistance
        5   |   5           |   5
        10  |   5           |   5
        5   |   10          |   5


    }

    // test when:
    // gas=5, distance=5, expectedDistance=5
    // gas=5, distance=10, expectedDistance=5
    // gas = 10, distance=5, expectedDistance=5
    // gas = 0, distance=1, expectedDistance=0




    // Mock
    def "When driver won't drive"() {
        given:
        Driver driver = Mock()
        driver.isWillingToDrive(10) >> false
        driver.isWillingToDrive(9) >> true

        @Subject
        Car car = new Car(driver)

        when:
        car.addGas(10)
        car.drive(10)

        then:
        car.getTotalDistanceDriven() == 0
    }


    // interactions
    def "Number of requests to isWillingToDrive incremented after driving"() {
        given:
        Driver driver = Spy()
        Car car = new Car(driver)

        when:
        car.addGas(5)
        car.drive(1)
        car.drive(1)

        then:
        1 * driver.isWillingToDrive(1)
        1 * driver.isWillingToDrive(1)

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
        car.totalDistanceDriven == expectedTotalDistance
        car.gasRemaining == expectedGasRemaining

        with(car) {
            totalDistanceDriven == expectedTotalDistance
            gasRemaining == expectedGasRemaining
        }


        where:
        gas  | distance || expectedTotalDistance | expectedGasRemaining
        5    |  3       ||   3                   |  2
        5    | 10       ||   5                   |  0
    }
}
