package MVCsem2.highergrade.adaptation;

import java.util.Random;

/**
 * Shows the difference between adapting a Java library class with inheritance
 * and adapting it with composition.
 */
public class RepairTimeAdaptationDemo {
    /**
     * Runs a small demonstration of both repair time generators.
     *
     * @param args The command line arguments. They are not used.
     */
    public static void main(String[] args) {
        InheritedRepairTimeRandom inheritedRandom = new InheritedRepairTimeRandom(7);
        ComposedRepairTimeRandom composedRandom = new ComposedRepairTimeRandom(new Random(7));

        System.out.println("Adaptation using inheritance:");
        System.out.println("Estimated repair time: " + inheritedRandom.nextRepairTimeMinutes() + " minutes");
        System.out.println();
        System.out.println("Adaptation using composition:");
        System.out.println("Estimated repair time: " + composedRandom.nextRepairTimeMinutes() + " minutes");
    }
}
