package MVCsem2.view;

import MVCsem2.controller.Controller;
import MVCsem2.controller.exception.OperationFailedException;
import MVCsem2.integration.exception.CustomerNotFoundException;
import MVCsem2.integration.register.CustomerRegistry;
import MVCsem2.integration.register.RepairOrderRegistry;
import MVCsem2.model.device.Printer;
import MVCsem2.model.dto.CustomerDetailsDTO;
import MVCsem2.model.dto.RepairOrderDTO;
import MVCsem2.model.entity.Bike;
import MVCsem2.model.entity.Customer;
import MVCsem2.model.entity.RepairOrder;
import MVCsem2.util.log.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests printouts produced by the View class.
 */
public class ViewTest {
    private PrintStream originalOut;
    private ByteArrayOutputStream printedOutput;
    private InMemoryLogger logger;

    @BeforeEach
    public void setUp() {
        RepairOrderRegistry.resetForTest();
        originalOut = System.out;
        printedOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(printedOutput));
        logger = new InMemoryLogger();
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
        RepairOrderRegistry.resetForTest();
    }

    @Test
    public void startShouldPrintScenarioInformation() {
        Controller controller = new Controller(new CustomerRegistry(),
                RepairOrderRegistry.getInstance(), new SilentPrinter());

        new View(controller, logger).start();

        String output = printedOutput.toString();
        assertContains(output, "Customer found:",
                "The view should print when a customer is found.");
        assertContains(output, "Name: Cristiano Ronaldo",
                "The view should print the found customer name.");
        assertContains(output, "Email: CR7@mail.com",
                "The view should print the found customer email.");
        assertContains(output, "Phone: 0737654321",
                "The view should print the found customer phone number.");
        assertContains(output, "No customer exists with phone number: 0700000000",
                "The view should print the not-found customer case.");
        assertContains(output, "The customer registry can not be reached. Please try again later.",
                "The view should print the database failure message.");
        assertContains(output, "All repair orders:",
                "The view should print the repair order list heading.");
        assertContains(output, "Repair order id: RO1",
                "The view should print RO1 in the repair order list.");
        assertContains(output, "Repair order id: RO2",
                "The view should print RO2 in the repair order list.");
        assertContains(output, "Repair order id: RO3",
                "The view should print RO3 in the repair order list.");
        assertEquals(3, countOccurrences(output, "Repair order id: RO4"),
                "The view should print RO4 in the list, before updates, and after updates.");
        assertEquals(6, countOccurrences(output, "Status: Pending"),
                "The view should print the pending status for all listed and shown repair orders.");
        assertEquals(5, countOccurrences(output, "Total cost: 0"),
                "The view should print zero cost for the four listed orders and the found order before updates.");
        assertContains(output, "Repair order found before diagnostics and repair tasks:",
                "The view should print when a repair order is found before updates.");
        assertContains(output, "Repair order after diagnostics and repair tasks:",
                "The view should print when the repair order has been updated.");
        assertContains(output, "Total cost: 1498",
                "The view should print the updated total cost.");
        assertFalse(logger.messages.isEmpty(),
                "The view should log the developer message for the simulated database failure.");
    }

    @Test
    public void startShouldPrintMissingRepairOrderMessages() {
        new View(new MissingRepairOrderController(), logger).start();

        String output = printedOutput.toString();
        assertContains(output, "No repair order found for phone number: 0737654321",
                "The view should print when no repair order is found by phone number.");
        assertContains(output, "Repair order not found: RO4",
                "The view should print when the updated repair order cannot be found.");
    }

    private void assertContains(String output, String expectedText, String message) {
        assertTrue(output.contains(expectedText), message);
    }

    private int countOccurrences(String output, String textToFind) {
        int count = 0;
        int index = 0;
        while ((index = output.indexOf(textToFind, index)) != -1) {
            count++;
            index += textToFind.length();
        }
        return count;
    }

    private static class MissingRepairOrderController extends Controller {
        MissingRepairOrderController() {
            super(new CustomerRegistry(), RepairOrderRegistry.getInstance(), new Printer());
        }

        @Override
        public CustomerDetailsDTO findCustomer(String phoneNumber)
                throws CustomerNotFoundException, OperationFailedException {
            if ("0700000000".equals(phoneNumber)) {
                throw new CustomerNotFoundException(phoneNumber);
            }
            if (CustomerRegistry.DATABASE_FAILURE_PHONE_NUMBER.equals(phoneNumber)) {
                throw new OperationFailedException("Could not search for customer.", new Exception("Offline"));
            }
            Customer customer = new Customer("Test Customer", "test@example.com", phoneNumber,
                    new Bike("Test", "Bike", "BIKE-TEST"));
            return new CustomerDetailsDTO(customer);
        }

        @Override
        public void createRepairOrder(String problemDescr, String customerPhone, String bikeSerialNo) {
        }

        @Override
        public RepairOrderDTO[] findAllRepairOrders() {
            return new RepairOrderDTO[0];
        }

        @Override
        public RepairOrderDTO findRepairOrder(String phoneNumber) {
            return null;
        }

        @Override
        public void addDiagnosticResult(String repairOrderId, String diagnosticResult) {
        }

        @Override
        public void addRepairTask(String repairOrderId, String repairTask, int cost) {
        }

        @Override
        public RepairOrderDTO getRepairOrderInfo(String repairOrderId) {
            return null;
        }

        @Override
        public void acceptRepairOrder(String repairOrderId) {
        }
    }

    private static class InMemoryLogger implements Logger {
        private final List<String> messages = new ArrayList<>();

        @Override
        public void log(String message) {
            messages.add(message);
        }
    }

    private static class SilentPrinter extends Printer {
        @Override
        public void printRepairOrder(RepairOrder repairOrder) {
        }
    }
}
