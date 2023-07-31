class WaitingEvent extends ActionEvent {

    WaitingEvent(double time, Customer customer, 
        ParentServer server) {
        super(time, customer, server);
    }

    @Override
    public boolean endEventCheck() {
        return false;
    }

    @Override
    public Pair<Event, Shop> execute(Shop shop) {
        if (server.getId() < shop.getCheckOutCounter()) {
            for (ParentServer k : shop.getAllServers()) {
                if (k.getId() == server.getId()) {
                    if (k.serveCheck(time)) {
                        return new Pair<Event, Shop>(new ServeEvent(k.getTimeFree(), 
                            customer, k), shop);
                    } else {
                        return new Pair<Event, Shop>(new WaitingEvent(
                            k.getTimeFree(), customer, k), shop);
                    }
                }
            }
        } else {
            double closestTimeFree = shop.getSelfCheckOuts().get(0).getTimeFree();
            for (SelfCheckOut k : shop.getSelfCheckOuts()) {
                if (k.serveCheck(time)) {
                    return new Pair<Event, Shop>(
                        new ServeEvent(k.getTimeFree(), customer, k), 
                        shop);
                }
                if (closestTimeFree > k.getTimeFree()) {
                    closestTimeFree = k.getTimeFree();
                }
            }
            return new Pair<Event, Shop>(new WaitingEvent(
                closestTimeFree, customer, server), shop);
        } 
        return new Pair<Event, Shop>(new WaitingEvent(
            server.getTimeFree(), customer, server), shop);
    }

    @Override
    public int updateNumServed(int i) {
        return i;
    }

    @Override 
    public String toString() {
        return "";
    }
}


