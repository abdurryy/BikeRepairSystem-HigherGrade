package MVCsem2.highergrade.adaptation;

import java.util.Random;

/**
 * Adapts java.util.Random by inheritance so it can create repair time estimates.
 */
public class InheritedRepairTimeRandom extends Random {
    private static final int MIN_REPAIR_MINUTES = 15;
    private static final int MAX_REPAIR_MINUTES = 120;

    /**
     * Creates a repair time generator with a chosen seed.
     *
     * @param seed The seed used by the inherited random generator.
     */
    public InheritedRepairTimeRandom(long seed) {
        super(seed);
    }

    /**
     * @return A random repair time estimate between 15 and 120 minutes.
     */
    public int nextRepairTimeMinutes() {
        int numberOfPossibleValues = MAX_REPAIR_MINUTES - MIN_REPAIR_MINUTES + 1;
        return MIN_REPAIR_MINUTES + nextInt(numberOfPossibleValues);
    }
}
