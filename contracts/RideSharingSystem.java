import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;


public class RideSharingSystem implements IRideSharingSystem {
    String line;
    String csvSplitBy = ",";
    public boolean loadRidersFromCSV(String ridersFilePath){
        try(BufferedReader br = new BufferedReader((new FileReader(ridersFilePath)))){
        while ((line = br.readLine()) != null) {
            String[] values = line.split(csvSplitBy);
            System.out.println("Record: " + String.join(", ", values));
            
        }
    }
         catch (IOException e){
            e.printStackTrace();
        }
        return true;
    }
        public boolean loadDriversFromCSV(String ridersFilePath){
        try(BufferedReader br = new BufferedReader((new FileReader(ridersFilePath)))){
        while ((line = br.readLine()) != null) {
            String[] values = line.split(csvSplitBy);
            System.out.println("Record: " + String.join(", ", values));
            
        }
    }
         catch (IOException e){
            e.printStackTrace();
        }
        return true;
    }

            public boolean loadRidesFromCSV(String ridersFilePath){
        try(BufferedReader br = new BufferedReader((new FileReader(ridersFilePath)))){
        while ((line = br.readLine()) != null) {
            String[] values = line.split(csvSplitBy);
            System.out.println("Record: " + String.join(", ", values));
            
        }
    }
         catch (IOException e){
            e.printStackTrace();
        }
        return true;
    }
    
}
        


