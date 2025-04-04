package DTO;

import java.util.Date;

public class Show {
    private String showId;
    private String cinemaName;
    private String movieName;
    private Date showDateTime;
    private double perSeatPrice;
    private int totalCapacity;
    private int bookedSeats;
    private boolean started;
    private boolean ended;

    public Show(String showId, String cinemaName, String movieName, Date showDateTime, double perSeatPrice, int totalCapacity) {
        this.showId = showId;
        this.cinemaName = cinemaName;
        this.movieName = movieName;
        this.showDateTime = showDateTime;
        this.perSeatPrice = perSeatPrice;
        this.totalCapacity = totalCapacity;
        this.bookedSeats = 0;
        this.started = false;
        this.ended = false;
    }

    public String getShowId() {
        return showId;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public String getMovieName() {
        return movieName;
    }
   
    public Date getShowDateTime() {
        return showDateTime;
    }

    public double getPerSeatPrice() {
        return perSeatPrice;
    }

    public void setPerSeatPrice(double perSeatPrice) {
        this.perSeatPrice = perSeatPrice;
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public int getBookedSeats() {
        return bookedSeats;
    }

    public int getAvailableSeats() {
        return totalCapacity - bookedSeats;
    }

    public boolean hasStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean hasEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    public void bookSeats(int numSeats) {
        this.bookedSeats += numSeats;
    }

    public void releaseSeats(int numSeats) {
        this.bookedSeats -= numSeats;
    }
}