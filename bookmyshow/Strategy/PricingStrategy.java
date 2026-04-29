package Strategy;

import java.util.List;

import Entities.Seat;

public interface PricingStrategy {
	double calculatePrice(List<Seat> seats);	
}
