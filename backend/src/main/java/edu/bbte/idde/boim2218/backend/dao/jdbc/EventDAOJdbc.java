package edu.bbte.idde.boim2218.backend.dao.jdbc;

import edu.bbte.idde.boim2218.backend.dao.EventDAO;
import edu.bbte.idde.boim2218.backend.exception.*;
import edu.bbte.idde.boim2218.backend.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

public class EventDAOJdbc implements EventDAO {
    private static final Logger logger = LoggerFactory.getLogger(EventDAOJdbc.class);


    @Override
    public int createEvent(Event event) {
        try (Connection connection = ConnectionPool.getDataSource().getConnection()) {
            var statement = connection.prepareStatement("INSERT INTO Event(EventTitle, EventLocation, "
                    + "EventDate, IsOnline, EventDescription) VALUES(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, event.getTitle());
            statement.setString(2, event.getLocation());
            statement.setDate(3, java.sql.Date.valueOf(event.getDate()));
            statement.setBoolean(4, event.isOnline());
            statement.setString(5, event.getDescription());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        logger.info("Event created with ID: {}", generatedId);
                        return generatedId;
                    }
                }
            }
            throw new CreateEventException("Creating event failed.");
        } catch (SQLException e) {
            logger.error("Error creating event: ", e);
            throw new DatabaseException("Error creating event", e);
        }
    }

    @Override
    public Event readEventById(int id) throws NotFoundException {
        try (Connection connection = ConnectionPool.getDataSource().getConnection()) {
            var statement = connection.prepareStatement("SELECT * FROM Event WHERE EventID = ?");
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Event event = mapToEvent(resultSet);
                    logger.info("Event found: {}", event);
                    return event;
                } else {
                    logger.warn("No event found with ID {}", id);
                    throw new NotFoundException("No event found with ID " + id);
                }
            }
        } catch (SQLException e) {
            logger.error("Error reading event by ID: ", e);
            throw new DatabaseException("Error reading event by ID: ", e);
        }
    }

    @Override
    public void updateEvent(int id, Event event) throws NotFoundException {
        try (Connection connection = ConnectionPool.getDataSource().getConnection()) {
            var statement = connection.prepareStatement(
                    "UPDATE Event SET EventTitle = ?, EventLocation = ?, EventDate = ?, IsOnline = ?, "
                            + "EventDescription = ? WHERE EventID = ?"
            );
            statement.setString(1, event.getTitle());
            statement.setString(2, event.getLocation());
            statement.setDate(3, java.sql.Date.valueOf(event.getDate()));
            statement.setBoolean(4, event.isOnline());
            statement.setString(5, event.getDescription());
            statement.setInt(6, id);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Event with ID {} updated successfully.", id);
            } else {
                logger.warn("No event found with ID {} for update", id);
                throw new NotFoundException("No event found with ID " + id);
            }
        } catch (SQLException e) {
            logger.error("Error updating event with ID {}", id, e);
            throw new DatabaseException("Error updating event with ID " + id, e);
        }
    }

    @Override
    public void deleteEventByID(int id) throws NotFoundException {
        try (Connection connection = ConnectionPool.getDataSource().getConnection()) {
            var statement = connection.prepareStatement("DELETE FROM Event WHERE EventID = ?");
            statement.setInt(1, id);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Event with ID {} deleted successfully.", id);
            } else {
                logger.warn("No event found with ID {} for deletion", id);
                throw new NotFoundException("No event found with ID " + id);
            }
        } catch (SQLException e) {
            logger.error("Error deleting event with ID {}", id, e);
            throw new DatabaseException("Error deleting event with ID " + id, e);
        }
    }

    @Override
    public List<Event> getEventList() {
        List<Event> events = new ArrayList<>();
        try (Connection connection = ConnectionPool.getDataSource().getConnection()) {
            var statement = connection.prepareStatement("SELECT * FROM Event");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    events.add(mapToEvent(resultSet));
                }
            }
            logger.info("Found {} events in the database.", events.size());
        } catch (SQLException e) {
            logger.error("Error finding events in the database.", e);
            throw new DatabaseException("Error finding events in the database.", e);
        }
        return events;
    }

    private Event mapToEvent(ResultSet resultSet) throws SQLException {
        Event event = new Event(
                resultSet.getString("EventTitle"),
                resultSet.getString("EventLocation"),
                resultSet.getDate("EventDate").toLocalDate(),
                resultSet.getBoolean("IsOnline"),
                resultSet.getString("EventDescription")
        );
        event.setId(resultSet.getInt("EventID"));
        return event;
    }
}
