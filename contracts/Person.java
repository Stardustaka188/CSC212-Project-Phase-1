public abstract class Person implements IPerson {
    private final int id;
    private String name;
    private String PhoneNumber;
    private LinkedList<IRide> Rides;

    public Person(int id, String name, String phoneNumber){
        this.id = id;
        this.name = name;
        setPhoneNumber(phoneNumber);
        Rides = new LinkedList<IRide>();
    }



public int getId(){
    return id;
}

public String getName(){
    return name;
}

public void setName(String name){
    this.name = name;
}

public String getPhoneNumber(){
    return PhoneNumber;
}

public void setPhoneNumber(String phonenumber)throws IllegalArgumentException{
    IllegalArgumentException e = new IllegalArgumentException();
    if(phonenumber.length() == 10){
        char c[] = phonenumber.toCharArray();
        for(int i =0; i < phonenumber.length(); i++){
            if(!Character.isDigit(c[i])){
        throw e;
            }
        }
        this.PhoneNumber = phonenumber;
        }
        else
            throw e;
        return;
} 

public LinkedList<IRide> getRideHistory()
{
    return Rides;

}
@Override
public String toString()
{
   return "Person {id=" + id + ", name='" + name + "', phoneNumber='" + PhoneNumber + "'}";
}
}
