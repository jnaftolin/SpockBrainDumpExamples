import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll
import spockdemo.Car
import spockdemo.FuelEfficiencyLogic

class CarTestReference extends Specification{

    def "The car can be filled with gas"() {
        given: "a car"
        Car car = new Car();

        when: "we add gas"
        car.addGas(5)

        then: "we should have gas"
        car.getGasRemaining() == 5

        when: "we add more gas"
        car.addGas(2)

        then: "we should have more gas"
        car.getGasRemaining() == 7
    }

    def "The car can't be driven more than the amount of gas in the car"() {
        given:
        Car car = new Car();
        car.addGas(5)

        when:
        car.drive(10)

        then:
        car.getTotalDistanceDriven() == 5
    }

    // Where (with unroll)
    //@Unroll
    def "The car correctly reports the amount driven when it is driven #distance with #gas gas"(){
        given:
        Car car = new Car();
        car.addGas(gas)

        when:
        car.drive(distance)

        then:
        car.getTotalDistanceDriven() == expected

        where:
        gas     | distance || expected
        10      | 1        || 1
        1       | 5        || 1
        5       | 5        || 5
        10      | 5        || 5
        5       | 10       || 5

    }

    // Mock
    def "When fuel efficiency logic is different"() {
        given:
        FuelEfficiencyLogic fuelEfficiencyLogic = Mock()
        fuelEfficiencyLogic.getRange(_) >> 10
        //fuelEfficiencyLogic.getRange(_) >> {int gas -> gas * 2 }

        Car car = new Car(fuelEfficiencyLogic);

        when:
        car.addGas(5)
        car.drive(10)

        then:
        car.getTotalDistanceDriven() == 10
    }


    // interactions
    def "Number of requests to fuelEfficiencyLogic incremented after driving"() {
        given:
        FuelEfficiencyLogic fuelEfficiencyLogic = Spy();
        Car car = new Car(fuelEfficiencyLogic)

        when:
        car.addGas(5)
        car.drive(1)
        car.drive(1)

        then:
        2 * fuelEfficiencyLogic.getRange(_)

        with(fuelEfficiencyLogic) {
            requests == 2
        }
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
        FuelEfficiencyLogic fuelEfficiencyLogic = Mock()
        Car car = new Car(fuelEfficiencyLogic)

        when:
        fuelEfficiencyLogic.getRange(_) >> { throw new InternalError("ouch") }
        car.drive(1)

        then:
        thrown(InternalError)
    }

    // with checks 2 values
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
