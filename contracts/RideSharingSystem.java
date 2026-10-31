
import java.io.*;

public class RideSharingSystem implements IRideSharingSystem {

    private IRiderList riderList;
    private IDriverList driverList;
    private IRideList rideList;

    public RideSharingSystem() {
        riderList = new RiderList();
        driverList = new DriverList();
        rideList = new RideList();
    }

    public boolean addDriver(IDriver driver) {
        if (driver == null) {
            return false;
        }
        if (driverList.findById(driver.getId()) != null) {
            return false;
        }
        if (driverList.findByVehiclePlate(driver.getVehiclePlate()) != null) {
            return false;
        }

        return driverList.add(driver);
    }

    public IRider searchRiderById(int riderId) {
        return riderList.findById(riderId);

    }

    public IRider searchRiderByEmail(String email) {
        return riderList.findByEmail(email);
    }

    public LinkedList<IRider> searchRidersByName(String fullName) {
        return riderList.findByName(fullName);
    }

    public LinkedList<IRider> searchRidersByHomeCity(String homeCity) {
        return riderList.findByHomeCity(homeCity);
    }

    public LinkedList<IRider> getAllRiders() {
        return riderList.getAll();
    }

    public IDriver searchDriverById(int driverId) {
        return driverList.findById(driverId);
    }

    public IDriver searchDriverByVehiclePlate(String vehiclePlate) {
        return driverList.findByVehiclePlate(vehiclePlate);
    }

    public LinkedList<IDriver> searchDriversByVehicleType(VehicleType vehicleType) {
        return driverList.findByVehicleType(vehicleType);
    }

    public LinkedList<IDriver> getAllDrivers() {
        return driverList.getAll();
    }

    public boolean removeRider(int riderId) {
        IRider rider = riderList.findById(riderId);
        if (rider == null) {
            return false;
        }
        LinkedList<IRide> history = rider.getRideHistory();
        if (!history.empty()) {
            history.findFirst();
            while (true) {
                IRide ride = history.retrieve();
                if (ride instanceof ISharedRide) {
                    ISharedRide shared = (ISharedRide) ride;
                    shared.removeParticipantById(riderId);
                    if (shared.isEmpty()) {
                        rideList.removeRideById(shared.getRideId());
                        removeFromHistory(shared.getDriver().getRideHistory(), shared.getRideId());
                    }
                } else {
                    rideList.removeRideById(ride.getRideId());
                    removeFromHistory(ride.getDriver().getRideHistory(), ride.getRideId());
                }
                if (history.last()) {
                    break;
                }
                history.findNext();
            }
        }
        return riderList.removeById(riderId);

    }

    private void removeFromHistory(LinkedList<IRide> history, int rideId) {
        if (history.empty()) {
            return;
        }
        history.findFirst();
        while (true) {
            if (history.retrieve().getRideId() == rideId) {
                history.remove();
                return;
            }
            if (history.last()) {
                return;
            }
            history.findNext();
        }
    }

    private void removeFromRidersHistories(IRide ride) {
        if (ride instanceof ISharedRide) {
            LinkedList<IRider> participants = ((ISharedRide) ride).getParticipants();
            if (participants.empty()) {
                return;
            }
            participants.findFirst();
            while (true) {
                removeFromHistory(participants.retrieve().getRideHistory(), ride.getRideId());
                if (participants.last()) {
                    break;
                }
                participants.findNext();
            }
        } else {
            removeFromHistory(((IPrivateRide) ride).getRider().getRideHistory(), ride.getRideId());
        }
    }

    public boolean schedulePrivateRide(String pickupLocation, IDateTime pickupTime,
            IDateTime dropoffTime, String dropoffLocation, int riderId, int driverId) {
        if (pickupTime == null || dropoffTime == null) {
            return false;
        }

        IRider rider = riderList.findById(riderId);
        IDriver driver = driverList.findById(driverId);
        if (rider == null || driver == null) {
            return false;
        }
        if (hasConflict(rider, pickupTime, dropoffTime)) {
            return false;
        }
        if (hasConflict(driver, pickupTime, dropoffTime)) {
            return false;
        }

        IPrivateRide ride = new PrivateRide(pickupLocation, pickupTime, dropoffTime,
                dropoffLocation, driver, rider);
        if (!rideList.addRide(ride)) {
            return false;
        }

        addToHistory(rider.getRideHistory(), ride);
        addToHistory(driver.getRideHistory(), ride);
        return true;
    }

    public boolean scheduleSharedRide(String pickupLocation, IDateTime pickupTime,
            IDateTime dropoffTime, String dropoffLocation, int[] riderIds, int driverId) {
        if (pickupTime == null || dropoffTime == null) {
            return false;
        }
        if (riderIds == null || riderIds.length == 0) {
            return false;
        }

        IDriver driver = driverList.findById(driverId);
        if (driver == null || hasConflict(driver, pickupTime, dropoffTime)) {
            return false;
        }

        for (int i = 0; i < riderIds.length; i++) {
            IRider rider = riderList.findById(riderIds[i]);
            if (rider == null || hasConflict(rider, pickupTime, dropoffTime)) {
                return false;
            }
        }

        ISharedRide ride = new SharedRide(pickupLocation, pickupTime, dropoffTime,
                dropoffLocation, driver);
        for (int i = 0; i < riderIds.length; i++) {
            IRider rider = riderList.findById(riderIds[i]);
            if (ride.addParticipant(rider)) {
                addToHistory(rider.getRideHistory(), ride);
            }
        }
        if (!rideList.addRide(ride)) {
            return false;
        }

        addToHistory(driver.getRideHistory(), ride);
        return true;
    }

    public LinkedList<IRide> searchRidesByPickupLocation(String pickupLocation) {
        return rideList.findByPickupLocation(pickupLocation);
    }

    public LinkedList<IRide> searchRidesByRiderName(String riderName) {
        return rideList.findByRiderName(riderName);
    }

    public LinkedList<IRider> getSharedRideParticipants(String pickupLocation) {
        LinkedList<IRider> result = new LinkedList<IRider>();
        LinkedList<IRide> rides = rideList.findByPickupLocation(pickupLocation);
        if (rides.empty()) {
            return result;
        }

        rides.findFirst();
        while (true) {
            IRide ride = rides.retrieve();
            if (ride instanceof ISharedRide) {
                LinkedList<IRider> participants = ((ISharedRide) ride).getParticipants();
                if (!participants.empty()) {
                    participants.findFirst();
                    while (true) {
                        result.insert(participants.retrieve());
                        if (participants.last()) {
                            break;
                        }
                        participants.findNext();
                    }
                }
            }
            if (rides.last()) {
                break;
            }
            rides.findNext();
        }
        return result;
    }

    public LinkedList<IRide> getAllRidesAlphabetically() {
        return rideList.getAllAlphabetically();
    }

    public boolean loadRidersFromCSV(String ridersFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(ridersFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] v = line.split(",");
                try {
                    IRider rider = new Rider(Integer.parseInt(v[0].trim()), v[1].trim(),
                            v[3].trim(), v[2].trim(), v[4].trim());
                    addRider(rider);
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                    continue;
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean loadDriversFromCSV(String driversFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(driversFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] v = line.split(",");
                try {
                    VehicleType type = VehicleType.valueOf(v[4].trim());
                    IDriver driver = new Driver(Integer.parseInt(v[0].trim()), v[1].trim(),
                            v[2].trim(), v[3].trim(), type);
                    addDriver(driver);
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                    continue;
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean loadRidesFromCSV(String ridesFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(ridesFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] v = line.split(",");
                try {
                    String type = v[0].trim();
                    IDateTime pickup = parseDateTime(v[2]);
                    IDateTime dropoff = parseDateTime(v[3]);
                    int driverId = Integer.parseInt(v[5].trim());

                    String[] idTexts = v[6].trim().split(";");
                    int[] riderIds = new int[idTexts.length];
                    for (int i = 0; i < idTexts.length; i++) {
                        riderIds[i] = Integer.parseInt(idTexts[i].trim());
                    }

                    if (type.equals("PRIVATE")) {
                        schedulePrivateRide(v[1].trim(), pickup, dropoff, v[4].trim(),
                                riderIds[0], driverId);
                    } else if (type.equals("SHARED")) {
                        scheduleSharedRide(v[1].trim(), pickup, dropoff, v[4].trim(),
                                riderIds, driverId);
                    }
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                    continue;
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean addRider(IRider rider) {
        if (rider == null) {
            return false;
        }
        if (riderList.findById(rider.getId()) != null) {
            return false;
        }
        return riderList.add(rider);
    }

    public boolean removeDriver(int driverId) {
        IDriver driver = driverList.findById(driverId);
        if (driver == null) {
            return false;
        }

        LinkedList<IRide> history = driver.getRideHistory();
        if (!history.empty()) {
            history.findFirst();
            while (true) {
                IRide ride = history.retrieve();
                rideList.removeRideById(ride.getRideId());
                removeFromRidersHistories(ride);
                if (history.last()) {
                    break;
                }
                history.findNext();
            }
        }
        return driverList.removeById(driverId);
    }

    private IDateTime parseDateTime(String text) {
        String[] parts = text.trim().split(" ");
        String[] date = parts[0].split("/");
        String[] time = parts[1].split(":");
        return new DateTime(Integer.parseInt(date[2]), Integer.parseInt(date[0]),
                Integer.parseInt(date[1]), Integer.parseInt(time[0]),
                Integer.parseInt(time[1]));
    }

    private boolean hasConflict(IPerson person, IDateTime start, IDateTime end) {
        LinkedList<IRide> history = person.getRideHistory();
        if (history.empty()) {
            return false;
        }

        history.findFirst();
        while (true) {
            IRide existing = history.retrieve();
            if (start.compareTo(existing.getDropoffTime()) < 0
                    && existing.getPickupTime().compareTo(end) < 0) {
                return true;
            }
            if (history.last()) {
                break;
            }
            history.findNext();
        }
        return false;
    }

    private void addToHistory(LinkedList<IRide> history, IRide ride) {
        if (!history.empty()) {
            history.findFirst();
        }
        history.insert(ride);
    }

}
