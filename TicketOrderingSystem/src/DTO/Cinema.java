package DTO;

import java.util.*;

public class Cinema {
    private String name;
    private boolean active;
    private double revenue;
    private List<String> shows;

    public Cinema(String name) {
        this.name = name;
        this.active = false;
        this.revenue = 0;
        this.shows = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double getRevenue() {
        return revenue;
    }

    public void addRevenue(double amount) {
        this.revenue += amount;
    }

    public void deductRevenue(double amount) {
        this.revenue -= amount;
    }

    public void addShow(String showId) {
        shows.add(showId);
    }

    public List<String> getShows() {
        return shows;
    }
}

