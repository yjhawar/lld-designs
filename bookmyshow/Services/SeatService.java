package Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import Entities.ScreenLayout;
import Entities.Seat;
import Entities.SeatType;
import Entities.Show;

public class SeatService {
	private final Map<Show, Map<Seat, String>> lockedSeats = new ConcurrentHashMap<>(); 

	public void lockSeats(Show show, List<Seat> seats, String user) {
		synchronized(show) {
            for (Seat seat : seats) {
                if (!show.isAvailable(seat)) {
                    throw new RuntimeException("Seat " + seat.getId() + " is already booked or locked.");
                }
            }
			// computeIfAbsent is single atomic operation that checks if the key exists and if not, 
			// creates a new value and puts it in the map.
			lockedSeats.computeIfAbsent(show, k -> new ConcurrentHashMap<>());
			for (Seat seat : seats) {
				show.lockSeat(seat);
				lockedSeats.get(show).put(seat, user);
			}
		}
	}

	public List<Seat> createSeats(ScreenLayout layout) {
		List<Seat> seats = new ArrayList<>();
		for(int r=0; r<layout.rows; r++) {
			for(int c=0; c<layout.cols; c++) {
				SeatType seatType = layout.rowToSeatType.getOrDefault(r, SeatType.REGULAR);
				String seatId = layout.screenId + "_" + r + "_" + c;
				seats.add(new Seat(seatId, r, c, seatType));
			}
		}
		return seats;
	}

}
