package edu.bbte.idde.boim2218.backend.dao;

import edu.bbte.idde.boim2218.backend.exception.NotFoundException;
import edu.bbte.idde.boim2218.backend.model.Event;


import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryEvent implements EventDAO {
    private Integer id = 1; //tárolási id
    private final Map<Integer, Event> events = new ConcurrentHashMap<>();   //minden id-nak megfeleltetünk egy eseményt

    @Override
    public int createEvent(Event event) {
        event.setId(id);
        events.put(id, event);
        id++;   //növeljük a sorszámot hozzáadás esetén
        return 0;
    }

    @Override
    public Event readEventById(int id) {
        if (!events.containsKey(id)) {  //amennyiben nem létezik az adott id-jú esemény, kivételt váltunk ki
            throw new NotFoundException("There is no event with ID:" + id);
        }

        return events.get(id);
    }

    @Override
    public void updateEvent(int id, Event event) {
        if (!events.containsKey(id)) {
            throw new NotFoundException("There is no event with ID:" + id);
        }
        Event existingEvent = events.get(id);

        existingEvent.setTitle(event.getTitle());
        existingEvent.setDate(event.getDate());
        existingEvent.setLocation(event.getLocation());
        existingEvent.setOnline(event.isOnline());
        existingEvent.setDescription(event.getDescription());
    }

    @Override
    public void deleteEventByID(int id) {
        if (!events.containsKey(id)) {
            throw new NotFoundException("There is no event with ID:" + id);
        }

        events.remove(id);
    }

    @Override
    public List<Event> getEventList() {
        return List.copyOf(events.values());
    }
}
