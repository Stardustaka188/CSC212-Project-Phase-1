public class Rider extends Person implements IRider, Comparable<IRider> {
    private String email;
    private String homeCity;

    public Rider(int id, String name, String phoneNumber, String email, String homeCity) {
        super(id, name, phoneNumber);
        this.email = email;
        this.homeCity = homeCity;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getHomeCity() {
        return homeCity;
    }

    @Override
    public void setHomeCity(String homeCity) {
        this.homeCity = homeCity;
    }

    @Override
    public int compareTo(IRider other) {
        if (other == null) return 1;
        return Integer.compare(this.getId(), other.getId());
    }
}