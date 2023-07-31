class Shop {
    private final ImList<Server> servers;
    private final ImList<SelfCheckOut> selfCheckOuts;
    private final ImList<ParentServer> allServers;
    private final int checkOutCounter;
    private static final int Number = 999999999;

    Shop(ImList<Server> servers, ImList<SelfCheckOut> selfCheckOuts) {
        this(servers, selfCheckOuts, processCounter(servers, selfCheckOuts), 
            processAllServers(servers, selfCheckOuts));
    }

    Shop(ImList<Server> servers, ImList<SelfCheckOut> selfCheckOuts, int checkOutCounter) {
        this(servers, selfCheckOuts, checkOutCounter, processAllServers(servers, selfCheckOuts));
    }

    Shop(ImList<Server> servers, ImList<SelfCheckOut> selfCheckOuts, 
        int checkOutCounter, ImList<ParentServer> allServers) {
        this.servers = servers;
        this.selfCheckOuts = selfCheckOuts;
        this.checkOutCounter = checkOutCounter;
        this.allServers = allServers;
    }

    public ImList<ParentServer> getAllServers() {
        return allServers;
    }

    public ImList<SelfCheckOut> getSelfCheckOuts() {
        return selfCheckOuts;
    }

    public ImList<Server> getServers() {
        return servers;
    }

    public Shop updateShopServed(ParentServer server, double time, double serviceTime) {
        if (server.getId() < checkOutCounter) {
            ImList<Server> currentServers = new ImList<Server>();
            for (int i = 0; i < servers.size(); i++) {
                if (servers.get(i).getId() == server.getId()) {
                    currentServers = currentServers.add(
                        servers.get(i).updateServer(time, serviceTime));
                } else {
                    currentServers = currentServers.add(servers.get(i));
                }
            }
            return new Shop(currentServers, selfCheckOuts, checkOutCounter);
        } else {
            ImList<SelfCheckOut> currentSelfCheckOuts = new ImList<SelfCheckOut>();
            if (selfCheckOuts.get(0).getNumberInQueue() > 0) {
                for (int i = 0; i < selfCheckOuts.size(); i++) {
                    if (selfCheckOuts.get(i).getId() == server.getId()) {
                        currentSelfCheckOuts = currentSelfCheckOuts.add(
                            selfCheckOuts.get(i).reduceNumberInQ().updateServer(time, serviceTime));
                    } else {
                        currentSelfCheckOuts = currentSelfCheckOuts.add(
                            selfCheckOuts.get(i).reduceNumberInQ());
                    }
                }
                return new Shop(servers, currentSelfCheckOuts, checkOutCounter);
            } else {
                for (int i = 0; i < selfCheckOuts.size(); i++) {
                    if (selfCheckOuts.get(i).getId() == server.getId()) {
                        currentSelfCheckOuts = currentSelfCheckOuts.add(
                            selfCheckOuts.get(i).updateServer(time, serviceTime));
                    } else {
                        currentSelfCheckOuts = currentSelfCheckOuts.add(selfCheckOuts.get(i));
                    }
                }
                return new Shop(servers, currentSelfCheckOuts, checkOutCounter);
            }
        }
    }

    public Shop addNumberInQueue(ParentServer server) {
        if (server.getId() < checkOutCounter) {
            ImList<Server> currentServers = new ImList<Server>();
            for (int i = 0; i < servers.size(); i++) {
                if (servers.get(i).getId() == server.getId()) {
                    currentServers = currentServers.add(servers.get(i).addNumberInQ());
                } else {
                    currentServers = currentServers.add(servers.get(i));
                }
            }
            return new Shop(currentServers, selfCheckOuts, checkOutCounter);
        } else {
            ImList<SelfCheckOut> currentSelfCheckOuts = new ImList<SelfCheckOut>();
            for (int i = 0; i < selfCheckOuts.size(); i++) {
                currentSelfCheckOuts = currentSelfCheckOuts.add(
                    selfCheckOuts.get(i).addNumberInQ());
            }
            return new Shop(servers, currentSelfCheckOuts, checkOutCounter);
        }
    }

    public Shop updateRest(ParentServer server, double time, double restTime) {
        ImList<Server> currentServers = new ImList<Server>();
        for (int i = 0; i < servers.size(); i++) {
            if (servers.get(i).getId() == server.getId()) {
                currentServers = currentServers.add(
                    servers.get(i).updateRestServer(time, restTime));
            } else {
                currentServers = currentServers.add(servers.get(i));
            }
        }
        return new Shop(currentServers, selfCheckOuts, checkOutCounter);
    }



    @Override
    public String toString() {
        String s = "";
        for (ParentServer k: getAllServers()) {
            s += k.getTimeFree();
        }
        return s;
    }

    public int getCheckOutCounter() {
        return checkOutCounter;
    }

    public static int processCounter(ImList<Server> servers, ImList<SelfCheckOut> selfCheckOuts) {
        if (!selfCheckOuts.isEmpty()) {
            return selfCheckOuts.get(0).getId();
        } else {
            return Number;
        }
    }

    public static ImList<ParentServer> processAllServers(ImList<Server> servers, 
        ImList<SelfCheckOut> selfCheckOuts) {
        ImList<ParentServer> list = new ImList<ParentServer>();
        return list.addAll(servers).addAll(selfCheckOuts);
    }
}

