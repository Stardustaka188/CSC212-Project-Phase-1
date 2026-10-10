public abstract class Person implements IPerson {
    private int id;
    private String name;
    private String PhoneNumber;
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

// public void setPhoneNumber(String phonenumber)throws IllegalArgumentException{
//     if(phonenumber.length() == 10){
//         char c[] = phonenumber.toCharArray();
//         for(int i =0; i<= phonenumber.length(); i++){
//             if(Character.isDigit(c[i]));
//             return;
//         }
//         }
//         else
//             throw IllegalArgumentException("Wrong format.");
    
// } <- not sure how this work, we'll see.
}
