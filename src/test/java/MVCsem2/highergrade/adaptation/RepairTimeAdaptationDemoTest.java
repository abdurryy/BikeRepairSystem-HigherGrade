package MVCsem2.highergrade.adaptation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the printout from the inheritance and composition adaptation demo.
 */
public class RepairTimeAdaptationDemoTest {
    private PrintStream originalOut;
    private ByteArrayOutputStream printedOutput;

    @BeforeEach
    public void setUp() {
        originalOut = System.out;
        printedOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(printedOutput));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void mainShouldPrintBothAdaptationExamples() {
        RepairTimeAdaptationDemo.main(new String[0]);

        String output = printedOutput.toString();
        assertTrue(output.contains("Adaptation using inheritance:"),
                "The demo should print the inheritance example heading.");
        assertTrue(output.contains("Adaptation using composition:"),
                "The demo should print the composition example heading.");
        assertTrue(output.contains("Estimated repair time:"),
                "The demo should print generated repair time estimates.");
    }
}
