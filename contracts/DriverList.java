/**
 * Uses only the course List cursor operations. getAll and collecting searches
 * are O(n) time and O(k) result space because insert is O(1).
 * Single removal is O(n); removing many matches is worst-case O(n^2),
 * since the course remove operation scans for the predecessor.
 */
public class DriverList implements IDriverList {
    private final LinkedList<IDriver> list = new LinkedList<>();
    private int count = 0;

    @Override
    public boolean add(IDriver driver) {
        if (driver == null) return false;
        if (findById(driver.getId()) != null || findByVehiclePlate(driver.getVehiclePlate()) != null) return false;
        if (list.empty()) {
            list.insert(driver);
        } else {
            list.findFirst();
            while (list.retrieve().getId() < driver.getId() && !list.last()) list.findNext();
            if (list.retrieve().getId() > driver.getId()) {
                // Insert before current using the course update/insert operations.
                IDriver displaced = list.retrieve();
                list.update(driver);
                list.insert(displaced);
            } else {
                list.insert(driver);
            }
        }
        count++;
        return true;
    }

    @Override
    public IDriver findById(int id) {
        if (list.empty()) return null;
        list.findFirst();
        while (true) {
            IDriver value = list.retrieve();
            if (value.getId() == id) return value;
            if (value.getId() > id || list.last()) return null;
            list.findNext();
        }
    }

    @Override
    public IDriver findByVehiclePlate(String key) {
        if (key == null || list.empty()) return null;
        list.findFirst();
        while (true) {
            if (key.equals(list.retrieve().getVehiclePlate())) return list.retrieve();
            if (list.last()) return null;
            list.findNext();
        }
    }

    @Override
    public LinkedList<IDriver> findByName(String key) {
        LinkedList<IDriver> result = new LinkedList<>();
        if (key == null || list.empty()) return result;
        list.findFirst();
        while (true) {
            if (key.equals(list.retrieve().getName())) result.insert(list.retrieve());
            if (list.last()) return result;
            list.findNext();
        }
    }

    @Override
    public LinkedList<IDriver> findByVehicleType(VehicleType key) {
        LinkedList<IDriver> result = new LinkedList<>();
        if (key == null || list.empty()) return result;
        list.findFirst();
        while (true) {
            if (key.equals(list.retrieve().getVehicleType())) result.insert(list.retrieve());
            if (list.last()) return result;
            list.findNext();
        }
    }

    @Override
    public LinkedList<IDriver> getAll() {
        LinkedList<IDriver> result = new LinkedList<>();
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
    public boolean removeByVehiclePlate(String key) {
        if (findByVehiclePlate(key) == null) return false;
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
    public int removeByVehicleType(VehicleType key) {
        if (key == null || list.empty()) return 0;
        int removed = 0;
        list.findFirst();
        while (true) {
            boolean wasLast = list.last();
            if (key.equals(list.retrieve().getVehicleType())) {
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