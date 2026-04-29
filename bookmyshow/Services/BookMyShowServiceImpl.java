package Services;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import Interfaces.BookMyShowService;
import Strategy.PricingStrategy;
import Entities.Booking;
import Entities.Cinema;
import Entities.Movie;
import Entities.Screen;
import Entities.ScreenLayout;
import Entities.Seat;
import Entities.Show;
import Entities.Ticket;

// Facade / controller/ orchestrator class.
// Think if this class as Project manager that knows the workflow.
public class BookMyShowServiceImpl implements BookMyShowService {

	private final BookingService bookingMgr; // think should tis be singletons
	private final CinemaService cinemaMgr;
	private final Map<String, Ticket> tickets;
	private List<String> users; // For simplicity, we can just keep a list of user names.
	private List<Movie> movies;

	// Singleton pattern to ensure only one instance of the service exists.
	// We can manage he creation of single service instance without using this pattern explicitly
	private static BookMyShowServiceImpl instance;

	private BookMyShowServiceImpl() {
		this.bookingMgr = new BookingService();
		this.cinemaMgr = new CinemaService();
		this.tickets = new ConcurrentHashMap<>();
		this.users = new ArrayList<>();
		this.movies = new ArrayList<>();
	}

	public static BookMyShowServiceImpl getInstance() {
        if (instance == null) {
            synchronized (BookMyShowServiceImpl.class) {
                if (instance == null) {
                    instance = new BookMyShowServiceImpl();
                }
            }
        }
        return instance;
    }

    public Ticket bookTickets(String user, String showId, List<Seat> desiredSeats) {
        Optional<Booking> bookingOptional = bookingMgr.createBooking(
                user,
                cinemaMgr.getShow(showId),
                desiredSeats
        );
		
		Ticket ticket = bookingOptional
        .map(b -> new Ticket(b.getId()))
        .orElseThrow(() -> 
            new RuntimeException("Booking failed for user: " + user)
        );
		tickets.put(user, ticket);
		return ticket;
	}


	public void addMovie(String id, String title, int durationInMinutes) {
		movies.add(new Movie(id, title, durationInMinutes));
	}

	public void addUser(String userName) {
		users.add(userName);
	}

	public Cinema addCinema(String id, String cityId, List<ScreenLayout> layout) {
		return cinemaMgr.addCinema(id, cityId, layout);
	}

	public Show addShow(String id, String title, Screen screen, LocalDateTime startTime, PricingStrategy pricingStrategy) {
		Movie movie = movies.stream()
							.filter(m -> m.getTitle().equals(title))
							.findFirst()
							.orElseThrow(() -> new RuntimeException("Movie not found: " + title));
		return cinemaMgr.addShow(id, movie, screen, startTime, pricingStrategy);
	}

	public List<Show> findShows(String movieTitle, String cityName) {
		return cinemaMgr.findShows(movieTitle, cityName);
	}

	public List<Seat> getAvailableSeats(String showId) {
		return cinemaMgr.getShow(showId).listAvailableSeats();
	}

}
