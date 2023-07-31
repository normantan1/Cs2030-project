abstract class ParentServer {
    private final int id;
    private final double timeFree;
    private final int numberInQueue;
    private final int qmax;

    ParentServer(int id, double timeFree, int numberInQueue, 
        int qmax) {
        this.id = id;
        this.timeFree = timeFree;
        this.numberInQueue = numberInQueue;
        this.qmax = qmax;
    }

    public int getId() {
        return this.id;
    }

    public double getTimeFree() {
        return timeFree;
    }

    public int getNumberInQueue() {
        return numberInQueue;
    }

    public int getQMax() {
        return qmax;
    }

    public boolean queueCheck() {
        return numberInQueue < qmax;
    }

    public abstract ParentServer updateServer(double currentTime, double serviceTime);

    public abstract ParentServer getThis();

    abstract ParentServer reduceNumberInQ();

    abstract ParentServer addNumberInQ();

    abstract boolean rest();

    abstract double getRestTime();

    abstract ParentServer updateRestServer(double time, double restTime);

    abstract boolean serveCheck(double time);

}
