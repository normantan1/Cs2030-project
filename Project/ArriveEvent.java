class ArriveEvent extends Event {
    
    ArriveEvent(double time, Customer customer) {
        super(time, customer);
    }

    public Pair<Event, Shop> execute(Shop shop) {
        for (ParentServer k : shop.getAllServers()) {
            if (k.serveCheck(customer.getArriveTime())) {
                return new Pair<Event, Shop>(
                    new ServeEvent(customer.getArriveTime(), customer, k), shop);
            }
        }
        for (ParentServer k : shop.getAllServers()) {
            if (k.queueCheck()) {
                return new Pair<Event, Shop>(new WaitEvent(customer.getArriveTime(), 
                    customer, k), shop);
            }
        }
        return new Pair<Event, Shop>(
            new LeaveEvent(time, customer), shop);
    }

    @Override
    public boolean endEventCheck() {
        return false;
    }

    @Override 
    public String toString() {
        return this.customer.toString() + " arrives\n";
    }

    @Override
    public int updateNumServed(int i) {
        return i;
    }
}


