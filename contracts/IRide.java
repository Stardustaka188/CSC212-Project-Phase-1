/**
	* Represents a generic ride in the ride-sharing system. Set the pickup and drop-off Date/Time values only in the constructor.
	Ride: This abstract class represents a generic ride in the Ride-Sharing System. A ride
has common attributes such as a pickup location, pickup date/time, drop-off date/time,
drop-off location, and an assigned driver. Ride is an abstract concept and must not be
instantiated directly. You must implement two concrete ride types: PrivateRide and
SharedRide. A private ride is scheduled with exactly one rider, whereas a shared ride
may be scheduled with multiple riders. Once a ride is created, its pickup and drop-off
date/time cannot be modified.

*/
public interface IRide extends Comparable<IRide> {

	// Returns the unique internal ride ID.
	int getRideId();

	// Returns the pickup location of the ride.
	String getPickupLocation();

	// Sets the pickup location of the ride.
	void setPickupLocation(String pickupLocation);

	// Returns the pickup date/time of the ride.
	IDateTime getPickupTime();

	// Returns the drop-off date/time of the ride.
	IDateTime getDropoffTime();

	// Returns the drop-off location of the ride.
	String getDropoffLocation();

	// Sets the drop-off location of the ride.
	void setDropoffLocation(String dropoffLocation);

	// Returns the driver assigned to this ride.
	IDriver getDriver();

	// Sets the driver assigned to this ride.
	void setDriver(IDriver driver);

	// Checks whether a rider participates in this ride.
	boolean hasRider(int riderId);

	// Returns a formatted string describing the ride.
	@Override
	String toString();

	// Compares rides alphabetically by pickup location.
	@Override
	int compareTo(IRide other);
}
