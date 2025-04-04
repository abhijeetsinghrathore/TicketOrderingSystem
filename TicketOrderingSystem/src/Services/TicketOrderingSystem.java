package Services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import DTO.Booking;
import DTO.Cinema;
import DTO.Show;
import Repository.TicketRepo;


/* In my solution we have to first register and start a cinema before registering a show
 * First register cinema
 * register_cinema("grand cinema")
 * Cinema registered: grand cinema
 * 
 *  start_cinema("grand cinema")
 *  Cinema started grand cinema
 * 
 * Now register a show
 * register_show("grand cinema","avengers", "02/05/2025 10:00 AM", 15.0, 100)
 * Show registered with ID: SHOW_1
 * 
 * 
 */

public class TicketOrderingSystem {
	
	
	private TicketRepo repo;
	private SimpleDateFormat dateFormat;


	public TicketOrderingSystem(TicketRepo repo, SimpleDateFormat dateFormat) {
		super();
		this.repo = repo;
		this.dateFormat = dateFormat;
	}

	public static void main(String[] args)
	{
		TicketRepo repo = new TicketRepo();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
		TicketOrderingSystem system = new TicketOrderingSystem(repo, dateFormat);
		try (Scanner sc = new Scanner(System.in)) {
			while(true)
			{
				String command = sc.nextLine();
				
				if(command.equals("exit"))
				{
					break;
				}
				
				system.processCommand(command);
			}
		}
		
	}

	private void processCommand(String command) {
		try {
			String[] parts = command.split("\\(",2);
			String operation = parts[0].trim();
			String params = "";
			
			if(parts.length>1)
			{
				params = parts[1].substring(0,parts[1].length()-1);
			}
			
			switch(operation)
			{
			case "register_cinema":
			{
				repo.registerCinema(params);
			}
			break;
			case "register_show":
			{
				repo.registerShow(params);
			}
			break;
			case "update_price":
			{
				repo.updatePrice(params);
			}
			break;
			case "start_cinema":
			{
				repo.startCinema(params);
			}
			break;
			case "end_cinema":
			{
				repo.endCinema(params);
			}
			break;
			case "start_show":
			{
				repo.startShow(params);
			}
			break;
			case "end_show":
			{
				repo.endShow(params);
			}
			break;
			case "order_ticket":
			{
				orderTicket(params);
			}
			break;
			case "cancel_booking":
			{
				cancelBooking(params);
			}
			break;
			case "print_system_stats":
			{
				printSystemStats();
			}
			break;
			default: 
				System.out.println("Invalid command" + operation);
			
			
			}
		}
		catch(Exception e)
		{
			System.out.println("Error processing command" + e.getMessage());
		}
		
	}
	
	
	public void orderTicket(String params) {
        String[] parts = params.split(",", 3);
        String movieName = parts[0].replaceAll("\"", "").trim();
        String datetimeStr = parts[1].replaceAll("\"", "").trim();
        int numSeats = Integer.parseInt(parts[2].trim());

        Date datetime;
        try {
            datetime = dateFormat.parse(datetimeStr);
        } catch (ParseException e) {
            System.out.println("Invalid date format: " + datetimeStr);
            return;
        }

        // Find the cheapest show for the movie
        Show cheapestShow = null;
        double lowestPrice = Double.MAX_VALUE;

        for (Show show : repo.getShows().values()) {
            if (show.getMovieName().equalsIgnoreCase(movieName) &&
                !show.hasEnded() &&
                show.getAvailableSeats() >= numSeats &&
                repo.getCinemas().get(show.getCinemaName()).isActive()) {
                   
                if (show.getPerSeatPrice() < lowestPrice) {
                    cheapestShow = show;
                    lowestPrice = show.getPerSeatPrice();
                }
            }
        }

        if (cheapestShow == null) {
            System.out.println("Booking not possible: Booking Unavailable (insufficient seats)");
            return;
        }

        if (cheapestShow.hasStarted()) {
            System.out.println("Booking not possible: Show already started");
            return;
        }

        // Create booking
        
        int bookingCounter = repo.getBookingIdCounter();
        String bookingId = "BOOKING_" + repo.getBookingIdCounter();
        repo.setBookingIdCounter(bookingCounter+1);
        double totalAmount = cheapestShow.getPerSeatPrice() * numSeats;
       
        Booking booking = new Booking(bookingId, cheapestShow.getShowId(), numSeats, totalAmount);
        repo.getBookings().put(bookingId, booking);
       
        // Update available seats
        cheapestShow.bookSeats(numSeats);
       
        // Update cinema revenue
        Cinema cinema = repo.getCinemas().get(cheapestShow.getCinemaName());
        cinema.addRevenue(totalAmount);

        System.out.println(numSeats + " tickets booked with total bill " + totalAmount + " (Booking ID: " + bookingId + ")");
    }
	
	
	 public void cancelBooking(String params) {
	        String bookingId = params.trim();
	        if (!repo.getBookings().containsKey(bookingId)) {
	            System.out.println("Booking not found: " + bookingId);
	            return;
	        }

	        Booking booking = repo.getBookings().get(bookingId);
	        Show show = repo.getShows().get(booking.getShowId());
	        Cinema cinema = repo.getCinemas().get(show.getCinemaName());

	        if (booking.isCancelled()) {
	            System.out.println("Booking already cancelled: " + bookingId);
	            return;
	        }

	        double refundAmount = 0;
	        if (!show.hasStarted()) {
	            // 50% refund if show hasn't started
	            refundAmount = booking.getTotalAmount() * 0.5;
	            cinema.deductRevenue(refundAmount);
	        }

	        booking.setCancelled(true);
	        show.releaseSeats(booking.getNumSeats());

	        System.out.println("Booking cancelled with refund amount: " + refundAmount);
	    }
	 
	 public void printSystemStats() {
	        System.out.println("System Statistics:");
	        System.out.println("=================");
	       
	        for (Cinema cinema : repo.getCinemas().values()) {
	            System.out.println("Cinema: " + cinema.getName() + ", Revenue: " + cinema.getRevenue() +
	                              ", Status: " + (cinema.isActive() ? "Active" : "Inactive"));
	        }
	    }



}
