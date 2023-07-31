import java.util.function.Supplier;

class Customer {
    private final int id;
    private final double arriveTime;
    private final Supplier<Double> serviceTime;

    Customer(int id, double arriveTime, Supplier<Double> serviceTime) {
        this.id = id;
        this.arriveTime = arriveTime;
        this.serviceTime = serviceTime;
    }

    public int getId() {
        return this.id;
    }

    public double getArriveTime() {
        return this.arriveTime;
    }

    public double getServiceTime() {
        return this.serviceTime.get();
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.arriveTime) + " " + this.id;
    }
}

