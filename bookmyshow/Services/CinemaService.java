package Services;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Entities.Cinema;
import Entities.Movie;
import Entities.Screen;
import Entities.ScreenLayout;
import Entities.Seat;
import Entities.SeatType;
import Entities.Show;
import Strategy.PricingStrategy;


public class CinemaService {

	private final Map<String, Cinema> cinemas;
    private final Map<String, Show> shows;
	private final SeatService seatMgr;

	public CinemaService() {
		this.cinemas = new HashMap<>();
		this.shows = new HashMap<>();
		this.seatMgr = new SeatService();
	}


	public Cinema addCinema(String id, String cityId, List<ScreenLayout> layout) {
		List<Screen> screens = new ArrayList<>();
		for(ScreenLayout screenLayout : layout) {
			List<Seat> seats = seatMgr.createSeats(screenLayout);
			screens.add(new Screen(screenLayout.screenId,id, seats));
		}
		Cinema cinema = new Cinema(id, cityId, screens);
		cinemas.put(cinema.getId(), cinema);
		return cinema;
	}

	public Show addShow(String id, Movie movie, Screen screen, LocalDateTime startTime, PricingStrategy pricingStrategy) {
		// NOTE: Conflicting addition of shows is not being handled here.
		Show show = new Show(id, movie, screen, startTime, pricingStrategy);
		shows.put(show.getId(), show);
		return show;
	}

	public List<Show> findShows(String movieTitle, String cityName) {
		List<Show> result = new ArrayList<>();
		for (Cinema cinema : cinemas.values()) {
			if (!cinema.getCityId().equalsIgnoreCase(cityName)) continue;
			for (Show show : shows.values()) {
				if (show.getScreen().getCinemaId().equals(cinema.getId()) &&
					show.getMovie().getTitle().equalsIgnoreCase(movieTitle)) {
					result.add(show);
				}
			}
		}
		return result;
	}

	public Show getShow(String showId) {
		return shows.get(showId);
	}
	
}
