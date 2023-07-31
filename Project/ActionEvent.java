abstract class ActionEvent extends Event {
    protected final ParentServer server;

    ActionEvent(double time, Customer customer, 
        ParentServer server) {
        super(time, customer);
        this.server = server;
    }

    public ParentServer getServer() {
        return server;
    }
}

