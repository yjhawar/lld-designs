package Strategy;

import java.util.List;

import Entities.Seat;

public class SurgePricingStrategy implements PricingStrategy {
	private final PricingStrategy baseStrategy;
    private final double surgeMultiplier;

    public SurgePricingStrategy(PricingStrategy baseStrategy, double surgeMultiplier) {
        this.baseStrategy = baseStrategy;
        this.surgeMultiplier = surgeMultiplier;
    }

    @Override
    public double calculatePrice(List<Seat> seats) {
        double basePrice = baseStrategy.calculatePrice(seats);
        return basePrice * surgeMultiplier;
    }
}
