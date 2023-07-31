import java.util.function.Supplier;

class Simulator {
    private final int numOfServers;
    private final int numOfSelfChecks;
    private final int qmax;
    private final ImList<Double> arrivalTimes;
    private final Supplier<Double> serviceTime;
    private final Supplier<Double> restTimes;

    
    Simulator(int numOfServers, int numOfSelfChecks, int qmax, ImList<Double> arrivalTimes,
        Supplier<Double> serviceTime, Supplier<Double> restTimes) {
        this.numOfServers = numOfServers;
        this.numOfSelfChecks = numOfSelfChecks;
        this.qmax = qmax;
        this.arrivalTimes = arrivalTimes;
        this.serviceTime = serviceTime;
        this.restTimes = restTimes;
    }

    public String finalCounter(double waitingTime, 
        int numServed, int numLeft) {
        if (numServed == 0) {
            return String.format("[%.3f %d %d]", 0.000, 
            numServed, numLeft);
        }
        return String.format("[%.3f %d %d]", waitingTime / numServed, 
            numServed, numLeft);
    }

    public ImList<Server> processServers() {
        ImList<Server> servers = new ImList<Server>();
        for (int i = 0; i < this.numOfServers; i++) {
            servers = servers.add(new Server(i + 1, 0.000, 
                0, qmax, restTimes));
        }
        return servers;
    }

    public ImList<SelfCheckOut> processSelfCheckOut() {
        if (!processServers().isEmpty()) {
            int id = processServers().get(processServers().size() - 1).getId() + 1;
            if (numOfSelfChecks > 0) {
                ImList<SelfCheckOut> servers = new ImList<SelfCheckOut>();
                for (int i = id; i < this.numOfSelfChecks + id; i++) {
                    servers = servers.add(new SelfCheckOut(i, 0.000, 
                        0, qmax));
                }
                return servers;
            }
        } else {
            ImList<SelfCheckOut> servers = new ImList<SelfCheckOut>();
            for (int i = 0; i < this.numOfSelfChecks; i++) {
                servers = servers.add(new SelfCheckOut(i + 1, 0.000, 
                    0, qmax));
            }
            return servers;
        }
        return new ImList<SelfCheckOut>();
    }

    public ImList<Customer> processCustomers() {
        ImList<Customer> customers = new ImList<Customer>();
        for (int i = 1; i <= this.arrivalTimes.size(); i++) {
            customers = customers.add(new Customer(i, arrivalTimes.get(i - 1),
            serviceTime));
        }
        return customers;
    }

    public int numOfCustomers() {
        return this.arrivalTimes.size();
    }

    public String simulate() {
        Shop currentShop = new Shop(processServers(), processSelfCheckOut());
        ImList<Customer> customers = processCustomers();
        PQ<Event> pq = new PQ<Event>(new TimeComp());
        String str = "";
        for (Customer currentCustomer : customers) {
            pq = pq.add(new ArriveEvent(currentCustomer.getArriveTime(), 
                currentCustomer));
        }
        int numServed = 0;
        double waitingTime = 0.000;
        while (!pq.isEmpty()) {
            Event currentEvent = pq.poll().first();
            str += currentEvent.toString();
            pq = pq.poll().second();
            numServed = currentEvent.updateNumServed(numServed);
            waitingTime += currentEvent.addWaitingTime();
            if (currentEvent.endEventCheck()) {
                continue;
            } else {
                Pair<Event, Shop> currentPair = currentEvent.execute(currentShop);
                pq = pq.add(currentPair.first());
                currentShop = currentPair.second();
            }
        }
        int numLeft = this.numOfCustomers() - numServed;
        str += finalCounter(waitingTime, numServed, numLeft);
        return str;
    }
}


