package com.hcl.VenueVista.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Venue {

    @Id
    private long id;

    private String name;
    private String location;
    private int capacity;

    // Default constructor
    public Venue() {
    }

    // Getters and Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}