/**
 * Represents a single rider in the ride-sharing system.
 * Riders are compared by ID.
 * Rider: This class extends Person and represents a single rider in the Ride-Sharing System.
In addition to the inherited Person fields, it should have fields for the rider’s email address
and home city. The rider ID is unique and must be provided when the rider is created
via the constructor. It must not be modified after creation.
 */
public interface IRider extends IPerson, Comparable<IRider> {

    //return the rider's email address.
    String getEmail();

    //Sets the rider's email address.
    void setEmail(String email);

    //return the rider's home city.
    String getHomeCity();

    //Sets the rider's home city.
    void setHomeCity(String homeCity);

    /**
     * Compares this rider with another rider based on rider ID.
     * Returns a negative integer, zero, or a positive integer as this
     * rider's ID is less than, equal to, or greater than the other
     * rider's ID. This ordering must be consistent with equality by ID.
     */
    @Override
    int compareTo(IRider other);
}
