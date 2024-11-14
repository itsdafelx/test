package edu.bbte.idde.boim2218.backend.model;

public abstract class BaseEntity {
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}