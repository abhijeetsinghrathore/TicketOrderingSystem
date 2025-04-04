package Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import DTO.Booking;
import DTO.Cinema;
import DTO.Show;


public class TicketRepo {
	private Map<String, Cinema> cinemas = new HashMap<>();
	private Map<String,Show> shows = new HashMap<>();
	private Map<String,Booking> bookings = new HashMap<>();
	private int showIdCounter = 1;
	private int bookingIdCounter =1;
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
	
	public void registerCinema(String params)
	{
		String cinemaName = params.replaceAll("\"", "");
		getCinemas().put(cinemaName,new Cinema(cinemaName));
		System.out.println("Cinema registered: "+ cinemaName);
	}
	
	public void registerShow(String params) {
        String[] parts = params.split(",", 5);
        String cinemaName = parts[0].replaceAll("\"", "").trim();
        String movieName = parts[1].replaceAll("\"", "").trim();
        String dateTimeStr = parts[2].replaceAll("\"", "").trim();
        double perSeatPrice = Double.parseDouble(parts[3].trim());
        int showCapacity = Integer.parseInt(parts[4].trim());

        if (!cinemas.containsKey(cinemaName)) {
            System.out.println("Cinema not found: " + cinemaName);
            return;
        }

        Cinema cinema = cinemas.get(cinemaName);
        if (!cinema.isActive()) {
            System.out.println("Cinema is not active: " + cinemaName);
            return;
        }
       
        Date dateTime;
        try {
            dateTime = dateFormat.parse(dateTimeStr);
        } catch (ParseException e) {
            System.out.println("Invalid date format: " + dateTimeStr);
            return;
        }

        String showId = "SHOW_" + showIdCounter++;
        Show show = new Show(showId, cinemaName, movieName, dateTime, perSeatPrice, showCapacity);
        shows.put(showId, show);
        cinema.addShow(showId);

        System.out.println("Show registered with ID: " + showId);
    }
	
	public void updatePrice(String params)
	{
		String parts[] = params.split(",",2); 
		String showId = parts[0].trim();
		double newPrice = Double.parseDouble(parts[1].trim());
		
		if(!getShows().containsKey(showId))
		{
			System.out.println("Show not found"+showId);
			return;
		}
		
		Show show = getShows().get(showId);
		if(show.hasStarted())
		{
			System.out.println("Connot update as show has already started");
			return;
		}
		
		show.setPerSeatPrice(newPrice);
		System.out.println("price udapted for show"+ showId);
	}
	
	public void startCinema(String params)
	{
		String cinemaName = params.replaceAll("\"", "");
		if(!getCinemas().containsKey(cinemaName))
		{
			System.out.println("Cinema not found "+cinemaName );
			return;
		}
		
		Cinema cinema = getCinemas().get(cinemaName);
		cinema.setActive(true);
		System.out.println("Cinema started"+ cinemaName);
		
	}
	
	public void endCinema(String params)
	{
		String cinemaName = params.replaceAll("\"", "");
		if(!getCinemas().containsKey(cinemaName))
		{
			System.out.println("Cinema not found "+cinemaName );
			return;
		}
		
		Cinema cinema = getCinemas().get(cinemaName);
		cinema.setActive(false);
		System.out.println("Cinema ended"+ cinemaName);
		
	}
	
	public void startShow(String params)
	{
		String showId = params.trim();
		if(!getShows().containsKey(showId))
		{
			System.out.println("Show not found"+ showId);
			return;
		}
		
		Show show = getShows().get(showId);
		show.setStarted(true);
		System.out.println("Show started "+showId);
		
	}
	
	public void endShow(String params)
	{
		String showId = params.trim();
		if(!getShows().containsKey(showId))
		{
			System.out.println("Show not found"+ showId);
			return;
		}
		
		Show show = getShows().get(showId);
		
		if(!show.hasStarted())
		{
			System.out.println("Cannot end a show that hasnt started");
			return;
		}
		
		show.setStarted(false);
		show.setEnded(true);
		System.out.println("Show ended "+showId);
		
	}

	public Map<String, Cinema> getCinemas() {
		return cinemas;
	}

	public void setCinemas(Map<String, Cinema> cinemas) {
		this.cinemas = cinemas;
	}

	public Map<String,Booking> getBookings() {
		return bookings;
	}

	public void setBookings(Map<String,Booking> bookings) {
		this.bookings = bookings;
	}

	public Map<String,Show> getShows() {
		return shows;
	}

	public void setShows(Map<String,Show> shows) {
		this.shows = shows;
	}

	public int getBookingIdCounter() {
		return bookingIdCounter;
	}

	public void setBookingIdCounter(int bookingIdCounter) {
		this.bookingIdCounter = bookingIdCounter;
	}	

}
