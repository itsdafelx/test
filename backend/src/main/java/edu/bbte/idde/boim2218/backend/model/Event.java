package edu.bbte.idde.boim2218.backend.model;

import java.time.LocalDate;

public class Event extends BaseEntity {
    private String title;
    private String location;
    private LocalDate date;
    private Boolean online;
    private String description;

    public Event(String title, String location, LocalDate date, Boolean online, String description) {
        super();
        this.title = title;
        this.location = location;
        this.date = date;
        this.online = online;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean isOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
