class SelfCheckOut extends ParentServer {

    SelfCheckOut(int id, double timeFree, int numberInQueue,
        int qmax) {
        super(id, timeFree, numberInQueue, qmax);
    }

    public int getId() {
        return super.getId();
    }

    public double getTimeFree() {
        return super.getTimeFree();
    }

    public int getNumberInQueue() {
        return super.getNumberInQueue();
    }

    public int getQMax() {
        return super.getQMax();
    }

    public boolean serveCheck(double time) {
        return time >= super.getTimeFree();
    }

    public SelfCheckOut updateServer(double currentTime, double serviceTime) {
        double freeTime = currentTime + serviceTime;
        return new SelfCheckOut(super.getId(), freeTime, getNumberInQueue(), getQMax());
    }

    public SelfCheckOut addNumberInQ() {
        return new SelfCheckOut(super.getId(), super.getTimeFree(), 
            getNumberInQueue() + 1, getQMax());
    }

    public SelfCheckOut reduceNumberInQ() {
        return new SelfCheckOut(super.getId(), 
            super.getTimeFree(), getNumberInQueue() - 1, getQMax());
    }

    public SelfCheckOut getThis() {
        return this;
    }

    public boolean rest() {
        return false;
    }
    
    public double getRestTime() {
        return 0.0;
    }

    @Override
    public String toString() {
        return String.format("self-check %d", super.getId());
    }

    public SelfCheckOut updateRestServer(double currentTime, double restTime) {
        return this;
    }

}
