/*List implementation is the same as slide,
write List interface with all the methods, and then implement
the interface through implements.
*/

public class LinkedList<T> implements List<T>


{
    private Node<T> head;
    private Node<T> current;

    public LinkedList(){
        head = null;
        current = null;
    }
    
    public void findFirst(){
        current = head;
    }
    public void findNext(){
        current = current.next;
    }

    public T retrieve(){
        T e = current.data;
        return e;
    }

    public void update(T e){
        current.data = e;
    }

    public void insert(T e){
        Node<T> tmp;
        if(empty()){
            head = current = new Node<T>(e);
        }
        else{
        tmp = current.next;
        current.next = new Node<T>(e);
        current = current.next;
        current.next = tmp;
        }

       /*  public void remove(){

        } remove,full,empty and last still not implemented. (Leave the implementation for me, Abdullah)*/ 
    }

}
