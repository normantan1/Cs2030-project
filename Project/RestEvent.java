public class RestEvent extends ActionEvent {
    RestEvent(double time, Customer customer, 
        ParentServer server) {
        super(time, customer, server);
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public Pair<Event, Shop> execute(Shop shop) {
        double restTime = server.getRestTime();
        return new Pair<Event, Shop>(
            new EndRestEvent(time + restTime, customer, server), 
            shop.updateRest(server, time, restTime));
    }

    @Override
    public boolean endEventCheck() {
        return false;
    }

    @Override
    public int updateNumServed(int i) {
        return i;
    }
}
