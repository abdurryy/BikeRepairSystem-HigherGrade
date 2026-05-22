package MVCsem2.model.device;

import MVCsem2.model.entity.RepairOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the repair order printout.
 */
public class PrinterTest {
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
    public void printRepairOrderShouldPrintRepairOrderInformation() {
        RepairOrder order = new RepairOrder("RO8", "Flat tire", "0702223344", "BIKE800");
        order.addRepairTask("Replace tube", 350);
        order.accept();

        new Printer().printRepairOrder(order);

        String output = printedOutput.toString();
        assertTrue(output.contains("Repair order id: RO8"),
                "The printout should include the repair order id.");
        assertTrue(output.contains("Problem: Flat tire"),
                "The printout should include the problem description.");
        assertTrue(output.contains("Customer phone number: 0702223344"),
                "The printout should include the customer phone number.");
        assertTrue(output.contains("Bike serial number: BIKE800"),
                "The printout should include the bike serial number.");
        assertTrue(output.contains("Status: Accepted"),
                "The printout should include the current status.");
        assertTrue(output.contains("Total cost: 350 kr"),
                "The printout should include the total cost.");
    }
}
