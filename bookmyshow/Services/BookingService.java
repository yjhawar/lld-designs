package Services;

import Entities.Seat;
import Entities.Show;
import java.util.List;
import java.util.Map;
import Entities.Booking;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Optional;
import java.util.UUID;

public class BookingService {
	private final Map<String, Booking> bookings;
	private final SeatService seatMgr;

	public BookingService() {
		this.bookings = new ConcurrentHashMap<>();
		this.seatMgr = new SeatService();
	}

	public Optional<Booking> createBooking(String user, Show show, List<Seat> seats) {
		seatMgr.lockSeats(show, seats, user);

		double price = show.getPricingStrategy().calculatePrice(seats);

		// Placeholder for payment processing result.Pass payment strategy as function argument.
		// If payment successful, confirm booking and generate ticket
		boolean paymentSuccessful = true; 

		if (!paymentSuccessful) {
			System.out.println("Payment failed for user: " + user);
			return Optional.empty();
		}

		Booking booking = new Booking.BookingBuilder()
								.setId(UUID.randomUUID().toString()) // Check if this thread safe
								.setUser(user)
								.setShow(show)
								.setSeats(seats)
								.setTotalAmount(price)
								.build();
		bookings.put(booking.getId(), booking);
		return Optional.of(booking);
	}
}
