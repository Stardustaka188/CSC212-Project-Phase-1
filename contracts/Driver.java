public class Driver extends Person implements IDriver {
    private  String vehiclePlate;
    private VehicleType vehicleType;
    
    public Driver(int id, String name, String phoneNumber, String vehiclePlate, VehicleType vehicleType){
        super(id,name,phoneNumber);
        setVehiclePlate(vehiclePlate);
        this.vehicleType = vehicleType;
    }
    public String getVehiclePlate(){return vehiclePlate;}
    public VehicleType getVehicleType(){return vehicleType;}
    public void setVehicleType(VehicleType vehicleType){this.vehicleType = vehicleType;}
    public void setVehiclePlate(String vehiclePlate){
        if (vehiclePlate == null || vehiclePlate.length() != 7) {
       throw new IllegalArgumentException("Invalid vehicle plate");
        }
       for(int i =0; i < 3; i++){
        if(vehiclePlate.charAt(i) < 'A' || vehiclePlate.charAt(i) > 'Z'){throw new IllegalArgumentException("Invalid vehicle plate");}
       }
        for(int i =3; i < 7; i++){
        if(vehiclePlate.charAt(i) < '0'|| vehiclePlate.charAt(i) > '9' ){throw new IllegalArgumentException("Invalid vehicle plate");}
       }
       this.vehiclePlate = vehiclePlate;
   }
   public int compareTo(IDriver other) {
       if (this.getId() < other.getId()) {
           return -1;
       } else if (this.getId() > other.getId()) {
           return 1;
       }
       return 0;
   }
    }