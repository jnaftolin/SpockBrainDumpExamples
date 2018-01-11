import spock.lang.*
import spockdemo.*

class CarTest extends Specification {


    // 1. basic test
    def "The car can be filled with gas"() {

    }

















    // 2. using 'where'
    def "The car correctly reports the amount driven when it has 5 gas and is driven a distance of 10"(){
        given:
        Car car = new Car();
        car.addGas(5)

        when:
        car.drive(10)

        then:
        car.getTotalDistanceDriven() == 5
    }




    // 3. Mocking
    def "When fuel efficiency logic is different"() {
        given:
        FuelEfficiencyLogic fuelEfficiencyLogic = Mock()

        fuelEfficiencyLogic.getRange(_) >> 10

        // fuelEfficiencyLogic.getRange(_) >> {int gas -> gas * 2 }

        Car car = new Car(fuelEfficiencyLogic);

        when:
        car.addGas(5)
        car.drive(10)

        then:
        car.getTotalDistanceDriven() == 10
    }


    // 4. interactions
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

    // 5. expect
    def "Car reports when it is able to drive the full distance"() {
        given:
        Car car = new Car()
        car.addGas(5)

        expect:
        car.drive(5)
    }


    // 6. expect and when
    @Unroll
    def "Car reports when it is able to drive the full distance with #gas gas and #distance distance"() {
        given:
        Car car = new Car()
        car.addGas(gas)

        expect:
        car.drive(distance) == didDriveFullAmount

        where:
        gas  | distance || didDriveFullAmount
        5    | 5        || true
        5    | 10       || false

    }


    // 7. exceptions
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
