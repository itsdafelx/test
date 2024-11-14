package edu.bbte.idde.boim2218.backend.service;

import edu.bbte.idde.boim2218.backend.dao.EventDAO;
import edu.bbte.idde.boim2218.backend.exception.EmptyDataException;
import edu.bbte.idde.boim2218.backend.model.Event;

import java.time.LocalDate;
import java.util.List;

public class CalendarService implements CalendarServiceInterface {
    private final EventDAO eventDao;

    public CalendarService(EventDAO eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public void addEvent(Event event) {
        verifyEvent(event);
        int newId = eventDao.createEvent(event);
        event.setId(newId);
    }

    @Override
    public void deleteEvent(int id) {
        eventDao.deleteEventByID(id);
    }

    @Override
    public void updateEvent(int id, Event event) {
        eventDao.updateEvent(id, event);
    }

    @Override
    public Event getEvent(int id) {
        return eventDao.readEventById(id);
    }

    @Override
    public List<Event> getEventList() {
        return eventDao.getEventList();
    }

    private void verifyEvent(Event event) {
        validateTitle(event.getTitle());
        validateDate(event.getDate());
        validateLocation(event.getLocation());
        validateDescription(event.getDescription());
        validateEventDate(event.getDate());
    }

    private void validateTitle(String title) {
        if (title == null) {
            throw new EmptyDataException("Event title cannot be empty!");
        }
    }

    private void validateDate(LocalDate date) {
        if (date == null) {
            throw new EmptyDataException("Event date cannot be empty!");
        }
    }

    private void validateLocation(String location) {
        if (location == null) {
            throw new EmptyDataException("Event location cannot be empty!");
        }
    }

    private void validateDescription(String description) {
        if (description == null) {
            throw new EmptyDataException("Event description cannot be empty!");
        }
    }

    private void validateEventDate(LocalDate date) {
        if (date != null && date.isBefore(LocalDate.now())) {
            throw new EmptyDataException("Event date cannot be in the past.");
        }
    }
}
