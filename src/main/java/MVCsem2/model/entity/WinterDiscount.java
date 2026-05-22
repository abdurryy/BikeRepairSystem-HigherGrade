package MVCsem2.model.entity;

/**
 * A discount strategy that applies a 20 percent winter discount to the total repair cost.
 */
public class WinterDiscount implements DiscountStrategy {
    /**
     * Applies a 20 percent discount to the specified total cost.
     *
     * @param totalCost The total cost before discount.
     * @return The total cost after the winter discount.
     */
    @Override
    public int applyDiscount(int totalCost) {
        return (int) (totalCost * 0.8);
    }
}