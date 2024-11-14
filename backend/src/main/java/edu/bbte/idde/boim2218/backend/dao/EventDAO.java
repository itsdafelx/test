package edu.bbte.idde.boim2218.backend.dao;

import edu.bbte.idde.boim2218.backend.exception.NotFoundException;
import edu.bbte.idde.boim2218.backend.model.Event;

import java.util.List;

public interface EventDAO {
    int createEvent(Event event);  //új esemény hozzáadása a naptárhoz

    Event readEventById(int id) throws NotFoundException;   //esemény visszakapása id alapján

    void updateEvent(int id, Event event) throws NotFoundException; //esemény adatainak módosítása

    void deleteEventByID(int id) throws NotFoundException;  //esemény törlése a naptárból

    List<Event> getEventList(); //összes esemény a naptárból
}
