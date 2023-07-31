import java.util.function.Supplier;

class Server extends ParentServer {
    private final Supplier<Double> restTimes;

    Server(int id, double timeFree, int numberInQueue,
        int qmax, Supplier<Double> restTimes) {
        super(id, timeFree, numberInQueue, qmax);
        this.restTimes = restTimes;
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

    public boolean queueCheck() {
        return getNumberInQueue() < getQMax();
    }

    public Server updateServer(double currentTime, double serviceTime) {
        double freeTime = currentTime + serviceTime;
        if (getNumberInQueue() > 0) {
            return new Server(super.getId(), freeTime, 
                getNumberInQueue() - 1, getQMax(), restTimes);
        }
        return new Server(super.getId(), freeTime, 
            getNumberInQueue(), getQMax(), restTimes);
    }

    public Server updateRestServer(double currentTime, double restTime) {
        double freeTime = currentTime + restTime;
        return new Server(super.getId(), freeTime, getNumberInQueue(), getQMax(), restTimes);
    }

    public Server addNumberInQ() {
        return new Server(super.getId(), super.getTimeFree(), 
            getNumberInQueue() + 1, getQMax(), restTimes);
    }

    public Server reduceNumberInQ() {
        return new Server(super.getId(), super.getTimeFree(), 
            getNumberInQueue() - 1, getQMax(), restTimes);
    }

    @Override
    public String toString() {
        return String.format("%d", super.getId());
    }

    public double getRestTime() {
        return restTimes.get();
    }

    public Server getThis() {
        return this;
    }

    public boolean rest() {
        return true;
    }
}


