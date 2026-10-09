import java.util.LinkedList;

/**
 * Stores all drivers in a structure maintained in sorted order by driver ID.
 * DriverList: This class will represent the linked list data structure used for storing drivers’
records. It should have methods for adding, searching, and removing driver records from
the list. Driver ID and vehicle plate are unique, so removing by either one removes at
most one driver. Name and vehicle type are not unique: removing by name or by vehicle
type must remove every driver that matches, not just the first one found.
 */
public interface IDriverList {

    // Inserts a driver into the list in sorted order by ID. If a driver with the same ID already exists, insertion fails.
    boolean add(IDriver driver);

    //Searches for a driver by ID.
    IDriver findById(int driverId);

    // Searches for all drivers with the given full name.
    LinkedList<IDriver> findByName(String fullName);

    //Searches for a driver by vehicle plate number.
    IDriver findByVehiclePlate(String vehiclePlate);

    //Returns all drivers with the specified vehicle type.
    LinkedList<IDriver> findByVehicleType(VehicleType vehicleType);

    //Returns all drivers in the linked list.
    LinkedList<IDriver> getAll();

    //Removes a driver by ID. Returns true if a driver with that ID was found and removed; false otherwise.
    boolean removeById(int driverId);

    //Removes a driver by vehicle plate number. Returns true if a driver with that plate was found and removed; false otherwise.
    boolean removeByVehiclePlate(String vehiclePlate);

    /**
     * Name and vehicle type are not unique, so more than one driver may match.
     * Removes every driver whose full name exactly matches the given name
     * (the same full-name equality used by findByName), not a first-name-only
     * or partial match.
     * Returns the number of drivers removed (0 if none matched).
     */
    int removeByName(String fullName);

    /**
     * Removes every driver with the specified vehicle type.
     * Returns the number of drivers removed (0 if none matched).
     */
    int removeByVehicleType(VehicleType vehicleType);

    //return the total number of drivers stored.
    int size();
}
