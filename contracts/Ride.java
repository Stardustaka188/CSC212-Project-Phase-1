/**
 * Ride: abstract parent of PrivateRide and SharedRide.
 * Holds the data every ride has (id, locations, times, driver). The times are
 * final so they can only be set in the constructor.
 */
public abstract class Ride implements IRide {

    private static int nextId = 1;   // used to give each ride a unique id

    private final int rideId;
    private String pickupLocation;
    private String dropoffLocation;
    private final IDateTime pickupTime;
    private final IDateTime dropoffTime;
    private IDriver driver;

    // Throws IllegalArgumentException if the data is empty or drop-off is not after pickup.
    public Ride(String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime,
                String dropoffLocation, IDriver driver) {
        checkLocation(pickupLocation);
        checkLocation(dropoffLocation);
        if (pickupTime == null || dropoffTime == null)
            throw new IllegalArgumentException("Times are required");
        if (pickupTime.compareTo(dropoffTime) >= 0)
            throw new IllegalArgumentException("Drop-off must be after pickup");

        this.rideId = nextId;
        nextId++;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.pickupTime = pickupTime;
        this.dropoffTime = dropoffTime;
        this.driver = driver;
    }

    // A location must not be null or empty.
    private void checkLocation(String location) {
        if (location == null || location.trim().isEmpty())
            throw new IllegalArgumentException("Location must not be empty");
    }

    public int getRideId() {
        return rideId;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        checkLocation(pickupLocation);
        this.pickupLocation = pickupLocation;
    }

    public IDateTime getPickupTime() {
        return pickupTime;
    }

    public IDateTime getDropoffTime() {
        return dropoffTime;
    }

    public String getDropoffLocation() {
        return dropoffLocation;
    }

    public void setDropoffLocation(String dropoffLocation) {
        checkLocation(dropoffLocation);
        this.dropoffLocation = dropoffLocation;
    }

    public IDriver getDriver() {
        return driver;
    }

    public void setDriver(IDriver driver) {
        this.driver = driver;
    }

    // Private and shared rides store riders differently, so they implement this.
    public abstract boolean hasRider(int riderId);

    // Text shared by both ride types; the subclasses add the riders.
    public String toString() {
        String driverText = "none";
        if (driver != null)
            driverText = driver.getName() + " (ID " + driver.getId() + ")";
        return "Ride #" + rideId + " | " + pickupLocation + " (" + pickupTime.format()
                + ") -> " + dropoffLocation + " (" + dropoffTime.format()
                + ") | Driver: " + driverText;
    }

    // Alphabetical order by pickup location (upper/lower case ignored).
    public int compareTo(IRide other) {
        return pickupLocation.compareToIgnoreCase(other.getPickupLocation());
    }
}