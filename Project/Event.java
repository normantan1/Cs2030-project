abstract class Event {
    protected final double time;
    protected final Customer customer;

    Event(double time, Customer customer) {
        this.time = time;
        this.customer = customer;
    }

    public double getTime() {
        return this.time;
    }

    public Customer getCustomer() {
        return this.customer;   
    }

    public double addWaitingTime() {
        return 0;
    }

    abstract boolean endEventCheck();

    abstract Pair<Event, Shop> execute(Shop shop);

    abstract int updateNumServed(int i);

}
