/**
 * Command-line entry point for the CSC212 Phase 1 project.
 * Startup CSV loading and the required menu are not implemented yet.
 */
public class Main {
    public static void main(String[] args) {
        // TODO: load riders, drivers and rides, then present the required menu.
        System.out.println("1. List all riders");
        System.out.println("2. Search for riders by home city.");
        System.out.println("3. Search for riders by name.");
        System.out.println("4. Add a rider.");
        System.out.println("5. List all drivers.");
        System.out.println("6. Search for drivers by vehicle type.");
        System.out.println("7. Add a driver.");
        System.out.println("8. List all rides alphabetically by pickup location");
        System.out.println("9. Search for rides by pickup location.");
        System.out.println("10. Search for rides by rider name.");
        System.out.println("11. List the participants of a shared ride by pickup location.");
        System.out.println("12. Add a ride "); //(ask the user whether it is private or shared, then prompt accordingly)
        System.out.println("0. Exit the program.");
    }
}
