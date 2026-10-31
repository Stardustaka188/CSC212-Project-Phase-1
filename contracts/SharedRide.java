/**
 * SharedRide: a ride with several riders (participants).
 * Extends Ride and keeps the riders in our LinkedList. A rider can't be added twice.
 */
public class SharedRide extends Ride implements ISharedRide {

    private LinkedList<IRider> participants = new LinkedList<IRider>();

    // The ride starts with no riders; use addParticipant to add them.
    public SharedRide(String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime,
                      String dropoffLocation, IDriver driver) {
        super(pickupLocation, pickupTime, dropoffTime, dropoffLocation, driver);
    }

    public LinkedList<IRider> getParticipants() {
        return participants;
    }

    // Adds the rider at the end of the list. Fails if null or already on the ride.
    public boolean addParticipant(IRider rider) {
        if (rider == null || hasRider(rider.getId()))
            return false;

        if (!participants.empty()) {
            participants.findFirst();
            while (!participants.last())   // go to the last node
                participants.findNext();
        }
        participants.insert(rider);        // insert puts it after the current node
        return true;
    }

    // Removes the rider with this id. Returns false if he is not on the ride.
    public boolean removeParticipantById(int riderId) {
        if (!hasRider(riderId))
            return false;
        participants.remove();             // hasRider left the current node on this rider
        return true;
    }

    public boolean isEmpty() {
        return participants.empty();
    }

    // Searches the list. If found, the current node is left on that rider.
    public boolean hasRider(int riderId) {
        if (participants.empty())
            return false;

        participants.findFirst();
        while (true) {
            if (participants.retrieve().getId() == riderId)
                return true;
            if (participants.last())
                return false;
            participants.findNext();
        }
    }

    public String toString() {
        String riders = "";
        if (!participants.empty()) {
            participants.findFirst();
            while (true) {
                IRider r = participants.retrieve();
                riders = riders + r.getName() + " (ID " + r.getId() + ")";
                if (participants.last())
                    break;
                riders = riders + ", ";
                participants.findNext();
            }
        }
        if (riders.equals(""))
            riders = "none";
        return "[SHARED] " + super.toString() + " | Riders: " + riders;
    }
}