package Entities;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import Strategy.PricingStrategy;


public class Show {
    private final String id;
    private final Movie movie;
    private final Screen screen;
    private final LocalDateTime startTime; 
    private final PricingStrategy pricingStrategy;
    private final Map<String, SeatStatus> seatStatusMap;

    public Show(String id, Movie movie, Screen screen, LocalDateTime startTime, PricingStrategy pricingStrategy) {
        this.id = id;
        this.movie = movie;
        this.screen = screen;
        this.startTime = startTime;
        this.pricingStrategy = pricingStrategy;
        this.seatStatusMap = new HashMap<>();
        for (Seat seat : screen.getSeats()) {
            seatStatusMap.put(seat.getId(), SeatStatus.AVAILABLE);
        }
    }

    public String getId() { return id; }
    public Movie getMovie() { return movie; }
    public Screen getScreen() { return screen; }
    public LocalDateTime getStartTime() { return startTime; }
    public PricingStrategy getPricingStrategy() { return pricingStrategy; }
    public boolean isAvailable(Seat seat) {
        return seatStatusMap.get(seat.getId()) == SeatStatus.AVAILABLE;
    }
    public List<Seat> listAvailableSeats(){
        return screen.getSeats().stream()
                .filter(seat -> seatStatusMap.get(seat.getId()) == SeatStatus.AVAILABLE)
                .toList();
    }
    public void lockSeat(Seat seat) {
        seatStatusMap.put(seat.getId(), SeatStatus.LOCKED);
    }
}