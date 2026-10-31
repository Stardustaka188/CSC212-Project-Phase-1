public class RiderList implements IRiderList{
    private LinkedList<IRider> riderLinkedList;
    public RiderList(){
        riderLinkedList = new LinkedList<>();
    }
    public IRider findById(int riderId){
        if(riderLinkedList.empty()) return null;
        riderLinkedList.findFirst();
        while(true){
        if (riderLinkedList.retrieve().getId()== riderId) return riderLinkedList.retrieve();
        if(riderLinkedList.last()) break;
        else riderLinkedList.findNext();
    }
    return null;
    }
}
