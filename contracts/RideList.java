/**
 * RideList: stores all rides in a linked list sorted by pickup location.
 * addRide inserts in the right place, so listing the rides is just one pass.
 */
public class RideList implements IRideList {

    // A node of the list of rides.
    private class RideNode {
        IRide ride;
        RideNode next;

        RideNode(IRide ride) {
            this.ride = ride;
        }
    }

    private RideNode head;
    private int size;

    // Adds the ride in sorted order. Fails if null or the ride id already exists.
    public boolean addRide(IRide ride) {
        if (ride == null)
            return false;

        // check that the id is not already used
        RideNode p = head;
        while (p != null) {
            if (p.ride.getRideId() == ride.getRideId())
                return false;
            p = p.next;
        }

        RideNode newNode = new RideNode(ride);
        if (head == null || ride.compareTo(head.ride) < 0) {
            // goes first
            newNode.next = head;
            head = newNode;
        } else {
            // go to the last node that is smaller than or equal to the new ride
            RideNode prev = head;
            while (prev.next != null && prev.next.ride.compareTo(ride) <= 0)
                prev = prev.next;
            newNode.next = prev.next;
            prev.next = newNode;
        }
        size++;
        return true;
    }

    // Removes the ride with this id. Returns false if it is not found.
    public boolean removeRideById(int rideId) {
        RideNode prev = null;
        RideNode p = head;
        while (p != null) {
            if (p.ride.getRideId() == rideId) {
                if (prev == null)
                    head = p.next;       // removing the first node
                else
                    prev.next = p.next;
                size--;
                return true;
            }
            prev = p;
            p = p.next;
        }
        return false;
    }

    // The list is already sorted, so we only copy it.
    public LinkedList<IRide> getAllAlphabetically() {
        LinkedList<IRide> result = new LinkedList<IRide>();
        RideNode p = head;
        while (p != null) {
            result.insert(p.ride);   // insert adds after the current (last) node
            p = p.next;
        }
        return result;
    }

    // Returns all rides with this pickup location (case is ignored).
    public LinkedList<IRide> findByPickupLocation(String pickupLocation) {
        LinkedList<IRide> result = new LinkedList<IRide>();
        if (pickupLocation == null)
            return result;

        RideNode p = head;
        while (p != null) {
            if (p.ride.getPickupLocation().equalsIgnoreCase(pickupLocation))
                result.insert(p.ride);
            p = p.next;
        }
        return result;
    }

    // Returns all rides that have a rider with exactly this full name.
    public LinkedList<IRide> findByRiderName(String riderFullName) {
        LinkedList<IRide> result = new LinkedList<IRide>();
        if (riderFullName == null)
            return result;

        RideNode p = head;
        while (p != null) {
            if (hasRiderNamed(p.ride, riderFullName))
                result.insert(p.ride);
            p = p.next;
        }
        return result;
    }

    // Checks the rider of a private ride, or every participant of a shared ride.
    private boolean hasRiderNamed(IRide ride, String name) {
        if (ride instanceof IPrivateRide) {
            IRider rider = ((IPrivateRide) ride).getRider();
            return rider.getName().equals(name);
        }

        LinkedList<IRider> participants = ((ISharedRide) ride).getParticipants();
        if (participants.empty())
            return false;

        participants.findFirst();
        while (true) {
            if (participants.retrieve().getName().equals(name))
                return true;
            if (participants.last())
                return false;
            participants.findNext();
        }
    }

    public int size() {
        return size;
    }
}