/**
 * DateTime: a point in time (year, month, day, hour, minute) that never changes.
 * I implemented IDateTime, checked the values in the constructor, and made
 * compareTo go year -> month -> day -> hour -> minute.
 */
public class DateTime implements IDateTime {

    private final int year;
    private final int month;
    private final int day;
    private final int hour;
    private final int minute;

    // Throws IllegalArgumentException if any value is not a real date/time.
    public DateTime(int year, int month, int day, int hour, int minute) {
        if (year < 1)
            throw new IllegalArgumentException("Invalid year");
        if (month < 1 || month > 12)
            throw new IllegalArgumentException("Invalid month");
        if (day < 1 || day > daysInMonth(year, month))
            throw new IllegalArgumentException("Invalid day");
        if (hour < 0 || hour > 23)
            throw new IllegalArgumentException("Invalid hour");
        if (minute < 0 || minute > 59)
            throw new IllegalArgumentException("Invalid minute");

        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    // Returns how many days the month has (February depends on leap year).
    private static int daysInMonth(int year, int month) {
        if (month == 4 || month == 6 || month == 9 || month == 11)
            return 30;
        if (month == 2) {
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                return 29;
            return 28;
        }
        return 31;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    // Returns the date as MM/DD/YYYY HH:MM
    public String format() {
        return String.format("%02d/%02d/%04d %02d:%02d", month, day, year, hour, minute);
    }

    // Compares the fields in order; the first one that differs decides.
    public int compareTo(IDateTime other) {
        if (year != other.getYear())
            return Integer.compare(year, other.getYear());
        if (month != other.getMonth())
            return Integer.compare(month, other.getMonth());
        if (day != other.getDay())
            return Integer.compare(day, other.getDay());
        if (hour != other.getHour())
            return Integer.compare(hour, other.getHour());
        return Integer.compare(minute, other.getMinute());
    }

    public String toString() {
        return format();
    }
}