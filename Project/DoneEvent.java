class DoneEvent extends ActionEvent {
    DoneEvent(double time, Customer customer, 
        ParentServer server) {
        super(time, customer, server);
    }

    @Override 
    public String toString() {
        return String.format("%.3f %d done serving by %s\n", 
        this.time, customer.getId(), server.toString());
    }

    @Override
    public boolean endEventCheck() {
        return server.rest() ? false : true;
    }

    @Override 
    public Pair<Event, Shop> execute(Shop shop) {
        return new Pair<Event, Shop>(
            new RestEvent(time, customer, server), shop);
    }

    @Override
    public int updateNumServed(int i) {
        return i;
    }
}

