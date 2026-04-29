package Strategy;

import java.util.List;

import Entities.Seat;
import Entities.SeatType;

public class SeatBasedPricingStrategy implements PricingStrategy {
	@Override
	public double calculatePrice(List<Seat> seats) {
		return seats.stream()
			.mapToDouble(seat -> seat.getType() == SeatType.PREMIUM ? 200 : 100)
			.sum();
	}
}
