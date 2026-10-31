/**
 * PrivateRide: a ride with exactly one rider.
 * Extends Ride and keeps a single rider that can never be null.
 */
public class PrivateRide extends Ride implements IPrivateRide {

    private IRider rider;

    public PrivateRide(String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime,
                       String dropoffLocation, IDriver driver, IRider rider) {
        super(pickupLocation, pickupTime, dropoffTime, dropoffLocation, driver);
        if (rider == null)
            throw new IllegalArgumentException("A private ride needs a rider");
        this.rider = rider;
    }

    public IRider getRider() {
        return rider;
    }

    public void setRider(IRider rider) {
        if (rider == null)
            throw new IllegalArgumentException("A private ride needs a rider");
        this.rider = rider;
    }

    // Only one rider, so just compare the id.
    public boolean hasRider(int riderId) {
        return rider.getId() == riderId;
    }

    public String toString() {
        return "[PRIVATE] " + super.toString() + " | Rider: " + rider.getName()
                + " (ID " + rider.getId() + ")";
    }
}