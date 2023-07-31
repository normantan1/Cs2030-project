class LeaveEvent extends Event {
    
    LeaveEvent(double time, Customer customer) {
        super(time, customer);
    }

    @Override 
    public String toString() {
        return this.customer.toString() + " leaves\n";
    }

    @Override
    public boolean endEventCheck() {
        return true;
    }

    @Override
    public Pair<Event, Shop> execute(Shop shop) {
        return new Pair<Event, Shop>(
            new LeaveEvent(time, customer), shop);
    }

    @Override
    public int updateNumServed(int i) {
        return i;
    }
}
