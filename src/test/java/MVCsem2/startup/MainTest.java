package MVCsem2.startup;

import MVCsem2.integration.register.RepairOrderRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the output from the application main method.
 */
public class MainTest {
    private PrintStream originalOut;
    private ByteArrayOutputStream printedOutput;

    @BeforeEach
    public void setUp() {
        RepairOrderRegistry.resetForTest();
        originalOut = System.out;
        printedOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(printedOutput));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
        RepairOrderRegistry.resetForTest();
    }

    @Test
    public void mainShouldPrintTheCompleteScenario() {
        Main.main(new String[0]);

        String output = printedOutput.toString();
        assertTrue(output.contains("Customer found:"),
                "The main method should start the view scenario.");
        assertTrue(output.contains("[NOTIFICATION - SYSTEM UPDATE]"),
                "The main method should connect the observer that prints repair order updates.");
        assertTrue(output.contains("Repair order id: RO4"),
                "The main method should print the final repair order.");
        assertTrue(output.contains("Status: Accepted"),
                "The final repair order should be accepted.");
        assertTrue(output.contains("Total cost: 1498 kr"),
                "The final repair order printout should include the accepted order cost.");
    }
}
