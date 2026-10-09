/**
 * Represents a single driver in the ride-sharing system.
 * Drivers are compared by ID.
 * Driver: This class extends Person and represents a single driver in the Ride-Sharing
System. In addition to the inherited Person fields, it should have fields for the driver’s
vehicle plate and vehicle type. The driver ID is unique and must be provided when the
driver is created via the constructor. It must not be modified after creation.
 */
public interface IDriver extends IPerson, Comparable<IDriver> {

    /**
     * return the driver's vehicle plate number.
     */
    String getVehiclePlate();

    /**
     * Sets the driver's vehicle plate number.
     * The plate must follow the format of exactly 3 uppercase letters
     * followed by 4 digits (e.g., "ABC1234"). Implementations must
     * validate the format and throw IllegalArgumentException if it does
     * not match. Vehicle plate numbers must be unique across all drivers
     * in the system; enforcing that uniqueness is the responsibility of
     * IDriverList/IRideSharingSystem when a driver is added.
     */
    void setVehiclePlate(String vehiclePlate);

    //return the driver's vehicle type.
    VehicleType getVehicleType();

    //Sets the driver's vehicle type.
    void setVehicleType(VehicleType vehicleType);

    /**
     * Compares this driver with another driver based on driver ID.
     * Returns a negative integer, zero, or a positive integer as this
     * driver's ID is less than, equal to, or greater than the other
     * driver's ID. This ordering must be consistent with equality by ID.
     */
    @Override
    int compareTo(IDriver other);
}
