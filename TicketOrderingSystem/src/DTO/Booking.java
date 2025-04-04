package DTO;

public class Booking {
    private String bookingId;
    private String showId;
    private int numSeats;
    private double totalAmount;
    private boolean cancelled;

    public Booking(String bookingId, String showId, int numSeats, double totalAmount) {
        this.bookingId = bookingId;
        this.showId = showId;
        this.numSeats = numSeats;
        this.totalAmount = totalAmount;
        this.cancelled = false;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getShowId() {
        return showId;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}