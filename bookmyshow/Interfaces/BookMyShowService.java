package Interfaces;

import java.time.LocalDateTime;
import java.util.List;

import Entities.Seat;
import Entities.Show;
import Entities.Ticket;
import Entities.Cinema;
import Entities.Screen;
import Entities.ScreenLayout;
import Strategy.PricingStrategy;

public interface BookMyShowService {
    Ticket bookTickets(String user, String showId, List<Seat> desiredSeats);
    List<Show> findShows(String movieTitle, String cityName);
    List<Seat> getAvailableSeats(String showId);

    Cinema addCinema(String id, String cityId, 
        List<ScreenLayout> layout);
    Show addShow(String id, String title,Screen screen, LocalDateTime startTime, PricingStrategy pricingStrategy);

    void addMovie(String id, String title, int durationInMinutes);
    void addUser(String userName);	
}
