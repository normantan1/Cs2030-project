public class EndRestEvent extends ActionEvent {
    EndRestEvent(double time, Customer customer, 
        ParentServer server) {
        super(time, customer, server);
    }

    @Override
    public Pair<Event, Shop> execute(Shop shop) {
        return new Pair<Event, Shop>(
            new EndRestEvent(time, customer, server), shop);
    }

    @Override
    public boolean endEventCheck() {
        return true;
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
