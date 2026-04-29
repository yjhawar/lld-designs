package Entities;

import java.util.ArrayList;
import java.util.List;

public class Screen {
    private final String id;
    private final String cinemaId;
    private final List<Seat> seats;

    public Screen(String id, String cinemaId, List<Seat> seats) {
        this.id = id;
        this.cinemaId = cinemaId;
        this.seats = seats != null ? seats : new ArrayList<>();
    }

    public String getId() { return id; }
    public String getCinemaId() { return cinemaId; }
    public List<Seat> getSeats() { return seats; }
}