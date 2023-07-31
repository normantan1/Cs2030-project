import java.util.Comparator;

class TimeComp implements Comparator<Event> {
    public int compare(Event event1, Event event2) {
        if (event1.getTime() - event2.getTime() < 0) {
            return -1;
        } else if (event2.getTime() - event1.getTime() < 0) {
            return 1;
        } else if (event1.getTime() == event2.getTime() && 
            event1.getCustomer().getId() != event2.getCustomer().getId()) {
            return event1.getCustomer().getId() - 
                event2.getCustomer().getId();
        } else {
            return event1.toString().compareTo(event2.toString());
        }
    } 
}


