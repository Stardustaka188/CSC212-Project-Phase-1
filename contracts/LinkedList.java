/*List implementation is the same as slide,
write List interface with all the methods, and then implement
the interface through implements.
*/

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
        }

        public void remove(){
            if(current == head){
                head = head.next;
            }
            else{
                Node<T> tmp = head;
                while(tmp.next != current){
                    tmp = tmp.next;
                }
            tmp.next = current.next;
            if(current.next == null)
                current = head;
            current = current.next;
            }
        }

        public boolean full(){
            return false;
        }

        public boolean empty(){
            return head == null;
        }


        public boolean last(){
            return current.next ==null;
        
        }
    }