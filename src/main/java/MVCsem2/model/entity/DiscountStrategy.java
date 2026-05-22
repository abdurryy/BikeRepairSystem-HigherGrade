package MVCsem2.model.entity;

/**
 * Strategy interface for calculating different types of discounts.
 * Part of the Strategy pattern implementation for Seminar 4.
 */
public interface DiscountStrategy {
    /**
     * Applies a discount to the total cost.
     * @param totalCost The original cost before discount.
     * @return The final cost after applying the strategy.
     */
    int applyDiscount(int totalCost);
}
