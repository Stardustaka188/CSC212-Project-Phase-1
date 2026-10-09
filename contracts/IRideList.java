import java.util.LinkedList;

/**
 * Stores all rides in a structure maintained in alphabetical order by pickup location.
 * RideList: This class will represent the linked list data structure used for storing the rides.
It should have methods for adding, searching, and removing rides from the list.

 */
public interface IRideList {

    //Adds a ride and maintains alphabetical ordering by pickup location.
    boolean addRide(IRide ride);

    //Removes a ride by its unique ride ID.
    boolean removeRideById(int rideId);

    //Returns all rides alphabetically ordered by pickup location.
    LinkedList<IRide> getAllAlphabetically();

    //Returns all rides whose pickup location matches the given location.
    LinkedList<IRide> findByPickupLocation(String pickupLocation);

    //Returns all rides that involve a rider with the given full name.
    LinkedList<IRide> findByRiderName(String riderFullName);

    //return number of rides stored.
    int size();
}
