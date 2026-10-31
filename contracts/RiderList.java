/**
 * Uses only the course List cursor operations. getAll and collecting searches
 * are O(n) time and O(k) result space because insert is O(1).
 * Single removal is O(n); removing many matches is worst-case O(n^2),
 * since the course remove operation scans for the predecessor.
 */
public class RiderList implements IRiderList {
    private final LinkedList<IRider> list = new LinkedList<>();
    private int count = 0;

    @Override
    public boolean add(IRider rider) {
        if (rider == null) return false;
        if (findById(rider.getId()) != null || findByEmail(rider.getEmail()) != null) return false;
        if (list.empty()) {
            list.insert(rider);
        } else {
            list.findFirst();
            while (list.retrieve().getId() < rider.getId() && !list.last()) list.findNext();
            if (list.retrieve().getId() > rider.getId()) {
                // Insert before current using the course update/insert operations.
                IRider displaced = list.retrieve();
                list.update(rider);
                list.insert(displaced);
            } else {
                list.insert(rider);
            }
        }
        count++;
        return true;
    }

    @Override
    public IRider findById(int id) {
        if (list.empty()) return null;
        list.findFirst();
        while (true) {
            IRider value = list.retrieve();
            if (value.getId() == id) return value;
            if (value.getId() > id || list.last()) return null;
            list.findNext();
        }
    }

    @Override
    public IRider findByEmail(String key) {
        if (key == null || list.empty()) return null;
        list.findFirst();
        while (true) {
            if (key.equals(list.retrieve().getEmail())) return list.retrieve();
            if (list.last()) return null;
            list.findNext();
        }
    }

    @Override
    public LinkedList<IRider> findByName(String key) {
        LinkedList<IRider> result = new LinkedList<>();
        if (key == null || list.empty()) return result;
        list.findFirst();
        while (true) {
            if (key.equals(list.retrieve().getName())) result.insert(list.retrieve());
            if (list.last()) return result;
            list.findNext();
        }
    }

    @Override
    public LinkedList<IRider> findByHomeCity(String key) {
        LinkedList<IRider> result = new LinkedList<>();
        if (key == null || list.empty()) return result;
        list.findFirst();
        while (true) {
            if (key.equals(list.retrieve().getHomeCity())) result.insert(list.retrieve());
            if (list.last()) return result;
            list.findNext();
        }
    }

    @Override
    public LinkedList<IRider> getAll() {
        LinkedList<IRider> result = new LinkedList<>();
        if (list.empty()) return result;
        list.findFirst();
        while (true) {
            result.insert(list.retrieve());
            if (list.last()) return result;
            list.findNext();
        }
    }

    @Override
    public boolean removeById(int id) {
        if (findById(id) == null) return false;
        list.remove();
        count--;
        return true;
    }

    @Override
    public boolean removeByEmail(String key) {
        if (findByEmail(key) == null) return false;
        list.remove();
        count--;
        return true;
    }

    @Override
    public int removeByName(String key) {
        if (key == null || list.empty()) return 0;
        int removed = 0;
        list.findFirst();
        while (true) {
            boolean wasLast = list.last();
            if (key.equals(list.retrieve().getName())) {
                list.remove();
                count--;
                removed++;
            } else if (!wasLast) {
                list.findNext();
            }
            // remove advances to the successor, but wraps at the tail.
            if (wasLast) return removed;
        }
    }

    @Override
    public int removeByHomeCity(String key) {
        if (key == null || list.empty()) return 0;
        int removed = 0;
        list.findFirst();
        while (true) {
            boolean wasLast = list.last();
            if (key.equals(list.retrieve().getHomeCity())) {
                list.remove();
                count--;
                removed++;
            } else if (!wasLast) {
                list.findNext();
            }
            if (wasLast) return removed;
        }
    }

    @Override
    public int size() { return count; }
}