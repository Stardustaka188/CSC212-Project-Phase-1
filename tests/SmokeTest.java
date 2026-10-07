/** Setup check only: this does not test ride-sharing system behaviour. */
public class SmokeTest {
    public static void main(String[] args) {
        // Ensure the test command actually enables assertions.
        boolean assertionsEnabled = false;
        assert assertionsEnabled = true;
        if (!assertionsEnabled) {
            throw new IllegalStateException("Run tests with java -ea.");
        }

        // Check that the entry point can be called without an exception.
        Main.main(new String[0]);

        // Check that the supplied enum is available to the test runner.
        assert VehicleType.values().length == 4 : "Expected four vehicle types";
        assert VehicleType.valueOf("SEDAN") == VehicleType.SEDAN;
        assert VehicleType.valueOf("LUXURY_SEDAN") == VehicleType.LUXURY_SEDAN;
        assert VehicleType.valueOf("SUV") == VehicleType.SUV;
        assert VehicleType.valueOf("VAN") == VehicleType.VAN;
    }
}
