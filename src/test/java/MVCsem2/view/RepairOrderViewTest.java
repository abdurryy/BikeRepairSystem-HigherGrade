package MVCsem2.view;

import MVCsem2.model.dto.RepairOrderDTO;
import MVCsem2.model.entity.RepairOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the observer view printout.
 */
public class RepairOrderViewTest {
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
    public void updateShouldPrintRepairOrderNotification() {
        RepairOrder order = new RepairOrder("RO9", "Loose chain", "0703334455", "BIKE900");
        order.addRepairTask("Adjust chain", 275);

        new RepairOrderView().update(new RepairOrderDTO(order));

        String output = printedOutput.toString();
        assertTrue(output.contains("[NOTIFICATION - SYSTEM UPDATE]"),
                "The observer view should print a notification heading.");
        assertTrue(output.contains("Repair Order ID: RO9"),
                "The observer view should print the repair order id.");
        assertTrue(output.contains("Current Status:  Pending"),
                "The observer view should print the current status.");
        assertTrue(output.contains("Total Cost:      275 kr"),
                "The observer view should print the total cost.");
    }
}
