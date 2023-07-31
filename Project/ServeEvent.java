class ServeEvent extends ActionEvent {

    ServeEvent(double time, Customer customer, 
        ParentServer server) {
        super(time, customer, server);
    }

    public Pair<Event, Shop> execute(Shop shop) {
        double serviceTime = customer.getServiceTime();
        return new Pair<Event, Shop>(
            new DoneEvent(serviceTime + 
            this.time, customer, server), shop.updateShopServed(server, time, serviceTime));
    }

    @Override 
    public String toString() {
        return String.format("%.3f %d serves by %s\n", this.time, 
            customer.getId(), server.toString());
    }

    @Override
    public boolean endEventCheck() {
        return false;
    }

    @Override
    public int updateNumServed(int i) {
        return i + 1;
    }

    @Override
    public double addWaitingTime() {
        return time - customer.getArriveTime();
    }

}
