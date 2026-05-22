package MVCsem2.highergrade.adaptation;

import java.util.Random;

/**
 * Adapts java.util.Random by composition so it can create repair time estimates.
 */
public class ComposedRepairTimeRandom {
    private static final int MIN_REPAIR_MINUTES = 15;
    private static final int MAX_REPAIR_MINUTES = 120;

    private final Random random;

    /**
     * Creates a repair time generator that delegates to the supplied Random object.
     *
     * @param random The random generator to use.
     */
    public ComposedRepairTimeRandom(Random random) {
        if (random == null) {
            throw new IllegalArgumentException("random must not be null");
        }
        this.random = random;
    }

    /**
     * @return A random repair time estimate between 15 and 120 minutes.
     */
    public int nextRepairTimeMinutes() {
        int numberOfPossibleValues = MAX_REPAIR_MINUTES - MIN_REPAIR_MINUTES + 1;
        return MIN_REPAIR_MINUTES + random.nextInt(numberOfPossibleValues);
    }
}
