package edu.bbte.idde.boim2218.backend.service;

import edu.bbte.idde.boim2218.backend.model.Event;

import java.util.List;

public interface CalendarServiceInterface {
    void addEvent(Event event);

    void deleteEvent(int id);

    void updateEvent(int id, Event event);

    Event getEvent(int id);

    List<Event> getEventList();
}
