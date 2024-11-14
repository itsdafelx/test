package edu.bbte.idde.boim2218.desktop.presentation;

import edu.bbte.idde.boim2218.backend.service.CalendarService;
import edu.bbte.idde.boim2218.backend.exception.NotFoundException;
import edu.bbte.idde.boim2218.backend.model.Event;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.format.DateTimeParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppUI {
    private static final Logger logger = LoggerFactory.getLogger(AppUI.class);
    private final CalendarService calendarService;
    private final JTable eventTable;
    private final DefaultTableModel tableModel;

    public AppUI(CalendarService calendarService) {
        this.calendarService = calendarService;

        JFrame frame = new JFrame("Calendar Manager Application");
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);

        String[] columns = {"ID", "Title", "Location", "Date", "Online", "Description"};

        tableModel = new DefaultTableModel(columns, 0);
        eventTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(eventTable);
        panel.add(scrollPane);

        JButton addButton = new JButton("Add Event");
        panel.add(addButton);

        JButton updateButton = new JButton("Update Event");
        panel.add(updateButton);

        JButton deleteButton = new JButton("Delete Event");
        panel.add(deleteButton);

        addButton.addActionListener(e -> addEvent());
        updateButton.addActionListener(e -> updateEvent());
        deleteButton.addActionListener(e -> deleteEvent());

        loadEvents();

        frame.setVisible(true);
    }

    private void loadEvents() {
        List<Event> events = calendarService.getEventList();
        for (Event event : events) {
            Object[] row = {
                    event.getId(),
                    event.getTitle(),
                    event.getLocation(),
                    event.getDate(),
                    event.isOnline(),
                    event.getDescription()
            };
            tableModel.addRow(row);
        }
    }

    private void addEvent() {
        String titleInput = JOptionPane.showInputDialog("Title:");
        String locationInput = JOptionPane.showInputDialog("Location:");
        String dateInput = JOptionPane.showInputDialog("Date (yyyy-mm-dd):");
        String onlineInput = JOptionPane.showInputDialog("Online? (true/false):");
        String descriptionInput = JOptionPane.showInputDialog("Description:");

        try {
            LocalDate date = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            boolean online = Boolean.parseBoolean(onlineInput);
            Event event = new Event(titleInput, locationInput, date, online, descriptionInput);

            calendarService.addEvent(event);
            logger.info("Event added: {}", event);

            Object[] row = {
                    event.getId(),
                    event.getTitle(),
                    event.getLocation(),
                    event.getDate(),
                    event.isOnline(),
                    event.getDescription()
            };
            tableModel.addRow(row);
            JOptionPane.showMessageDialog(null, "Event added successfully!");

        } catch (DateTimeParseException e) {
            logger.error("Invalid date format: {}", dateInput, e);
            JOptionPane.showMessageDialog(null, "Invalid date format!");
        } catch (IllegalArgumentException e) {
            logger.error("Error adding event: {}", e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void updateEvent() {
        int selected = eventTable.getSelectedRow();
        if (selected == -1) {
            JOptionPane.showMessageDialog(null, "No event selected!");
            return;
        }

        int eventId = (int) tableModel.getValueAt(selected, 0);
        String titleInput = JOptionPane.showInputDialog("New Title:", tableModel.getValueAt(selected, 1));
        String locationInput = JOptionPane.showInputDialog("New Location:", tableModel.getValueAt(selected, 2));
        String dateInput = JOptionPane.showInputDialog("New Date (yyyy-mm-dd):", tableModel.getValueAt(selected, 3));
        String onlineInput = JOptionPane.showInputDialog("Online? (true/false):", tableModel.getValueAt(selected, 4));
        String descriptionInput = JOptionPane.showInputDialog("New Description:", tableModel.getValueAt(selected, 5));

        try {
            LocalDate date = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            boolean online = Boolean.parseBoolean(onlineInput);

            Event updatedEvent = new Event(titleInput, locationInput, date, online, descriptionInput);
            calendarService.updateEvent(eventId, updatedEvent);
            logger.info("Event updated: ID={}, New Event={}", eventId, updatedEvent);

            tableModel.setRowCount(0);
            loadEvents();

            JOptionPane.showMessageDialog(null, "Event updated successfully!");
        } catch (DateTimeParseException e) {
            logger.error("Invalid date format: {}", dateInput, e);
            JOptionPane.showMessageDialog(null, "Invalid date format!");
        } catch (NotFoundException e) {
            logger.error("Event not found: ID={}", eventId, e);
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void deleteEvent() {
        int selected = eventTable.getSelectedRow();
        if (selected == -1) {
            JOptionPane.showMessageDialog(null, "No event selected!");
            return;
        }

        int eventId = (int) tableModel.getValueAt(selected, 0);
        try {
            calendarService.deleteEvent(eventId);
            logger.info("Event deleted: ID={}", eventId);
            JOptionPane.showMessageDialog(null, "Event deleted successfully!");
        } catch (NotFoundException e) {
            logger.error("Event not found: ID={}", eventId, e);
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        tableModel.setRowCount(0);
        loadEvents();
    }
}
