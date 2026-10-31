
import java.io.*;

public class Main {

    private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {

        RideSharingSystem system = new RideSharingSystem();
        

        //loading files
        if (!system.loadRidersFromCSV("riders_100.csv")) {
            System.out.println("Failed to load riders file.");
        }
        if (!system.loadDriversFromCSV("drivers_30.csv")) {
            System.out.println("Failed to load drivers file.");
        }
        if (!system.loadRidesFromCSV("rides_40.csv")) {
            System.out.println("Failed to load rides file.");
        }

        //while loop for choosing options
        int choice = -1;
        while (choice != 0) {
            printMenu();
            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    printList(system.getAllRiders());
                    break;
                case 2:
                    printList(system.searchRidersByHomeCity(readLine("Home city: ")));
                    break;
                case 3:
                    printList(system.searchRidersByName(readLine("Full name: ")));
                    break;
                case 4:
                    addRiderOption(system);
                    break;
                case 5:
                    printList(system.getAllDrivers());
                    break;
                case 6:
                    printList(system.searchDriversByVehicleType(readVehicleType()));
                    break;
                case 7:
                    addDriverOption(system);
                    break;
                case 8:
                    printList(system.getAllRidesAlphabetically());
                    break;
                case 9:
                    printList(system.searchRidesByPickupLocation(readLine("Pickup location: ")));
                    break;
                case 10:
                    printList(system.searchRidesByRiderName(readLine("Rider name: ")));
                    break;
                case 11:
                    printList(system.getSharedRideParticipants(readLine("Pickup location: ")));
                    break;
                case 12:
                    addRideOption(system);
                    break;
                case 0:
                    System.out.println("Goodbye.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // prints menu options
    private static void printMenu(){
        System.out.println();
        System.out.println("1. List all riders");
        System.out.println("2. Search for riders by home city");
        System.out.println("3. Search for riders by name");
        System.out.println("4. Add a rider");
        System.out.println("5. List all drivers");
        System.out.println("6. Search for drivers by vehicle type");
        System.out.println("7. Add a driver");
        System.out.println("8. List all rides alphabetically by pickup location");
        System.out.println("9. Search for rides by pickup location");
        System.out.println("10. Search for rides by rider name");
        System.out.println("11. List the participants of a shared ride by pickup location");
        System.out.println("12. Add a ride");
        System.out.println("0. Exit");
    }
    // prints list of any type
    private static <T> void printList(LinkedList<T> list) {
        if (list == null || list.empty()) {
            System.out.println("No results found.");
            return;
        }
        list.findFirst();
        while (true) {
            System.out.println(list.retrieve());
            if (list.last()) {
                break;
            }
            list.findNext();
        }
    }
    //reads string input
    private static String readLine(String prompt) throws IOException {
        System.out.print(prompt);
        String line = input.readLine();
        return line == null ? "" : line.trim();
    }
     //reads int input
    private static int readInt(String prompt) throws IOException {
        while (true) {
            String text = readLine(prompt);
            try {
                return Integer.parseInt(text);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    // reads vehicle type
    private static VehicleType readVehicleType() throws IOException {
        while (true) {
            String text = readLine("Vehicle type (SEDAN, LUXURY_SEDAN, SUV, VAN): ");
            try {
                return VehicleType.valueOf(text.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid vehicle type.");
            }
        }
    }
    // adding a rider
        private static void addRiderOption(RideSharingSystem system) throws IOException {
        int id = readInt("Rider ID: ");
        String name = readLine("Full name: ");
        String phone = readLine("Phone number (10 digits): ");
        String email = readLine("Email: ");
        String city = readLine("Home city: ");
        try {
            IRider rider = new Rider(id, name, phone, email, city);
            if (system.addRider(rider)) {
                System.out.println("Rider added successfully.");
            } else {
                System.out.println("Failed to add rider: the ID already exists.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to add rider: " + e.getMessage());
        }
    }
    // adding a driver
    private static void addDriverOption(RideSharingSystem system) throws IOException {
        int id = readInt("Driver ID: ");
        String name = readLine("Full name: ");
        String phone = readLine("Phone number (10 digits): ");
        String plate = readLine("Vehicle plate (e.g. ABC1234): ");
        VehicleType type = readVehicleType();
        try {
            IDriver driver = new Driver(id, name, phone, plate, type);
            if (system.addDriver(driver)) {
                System.out.println("Driver added successfully.");
            } else {
                System.out.println("Failed to add driver: the ID or the plate already exists.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to add driver: " + e.getMessage());
        }
    }
    // adding ride options like location, time etc..
        private static void addRideOption(RideSharingSystem system) throws IOException {
        String type = readLine("Ride type (private/shared): ").toLowerCase();
        if (!type.equals("private") && !type.equals("shared")) {
            System.out.println("Invalid ride type.");
            return;
        }

        String pickupLocation = readLine("Pickup location: ");
        IDateTime pickupTime = readDateTime("Pickup time (MM/DD/YYYY HH:MM): ");
        IDateTime dropoffTime = readDateTime("Drop-off time (MM/DD/YYYY HH:MM): ");
        String dropoffLocation = readLine("Drop-off location: ");
        int driverId = readInt("Driver ID: ");

        boolean scheduled;
        if (type.equals("private")) {
            int riderId = readInt("Rider ID: ");
            scheduled = system.schedulePrivateRide(pickupLocation, pickupTime, dropoffTime,
                    dropoffLocation, riderId, driverId);
        } else {
            int[] riderIds = readRiderIds("Rider IDs separated by commas: ");
            if (riderIds.length < 2) {
                System.out.println("A shared ride needs at least 2 riders.");
                return;
            }
            scheduled = system.scheduleSharedRide(pickupLocation, pickupTime, dropoffTime,
                    dropoffLocation, riderIds, driverId);
        }

        if (scheduled) {
            System.out.println("Ride scheduled successfully.");
        } else {
            System.out.println("Failed to schedule the ride.");
        }
    }
    // reads a date and time in the format MM/DD/YYYY
    private static IDateTime readDateTime(String prompt) throws IOException {
        while (true) {
            String text = readLine(prompt);
            try {
                String[] parts = text.split(" ");
                String[] date = parts[0].split("/");
                String[] time = parts[1].split(":");
                return new DateTime(Integer.parseInt(date[2]), Integer.parseInt(date[0]),
                        Integer.parseInt(date[1]), Integer.parseInt(time[0]),
                        Integer.parseInt(time[1]));
            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Invalid date. Use MM/DD/YYYY HH:MM, for example 10/28/2026 08:45");
            }
        }
    }


    
    // reads id
    private static int[] readRiderIds(String prompt) throws IOException {
        while (true) {
            String[] parts = readLine(prompt).split(",");
            int[] ids = new int[parts.length];
            try {
                for (int i = 0; i < parts.length; i++) {
                    ids[i] = Integer.parseInt(parts[i].trim());
                }
                return ids;
            } catch (NumberFormatException e) {
                System.out.println("Please enter numbers separated by commas.");
            }
        }
    }


}
