package MVCsem2.controller;

import MVCsem2.controller.exception.OperationFailedException;
import MVCsem2.integration.exception.CustomerNotFoundException;
import MVCsem2.integration.register.CustomerRegistry;
import MVCsem2.integration.register.RepairOrderRegistry;
import MVCsem2.model.device.Printer;
import MVCsem2.model.dto.CustomerDetailsDTO;
import MVCsem2.model.dto.RepairOrderDTO;
import MVCsem2.model.entity.RepairOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests controller behavior, especially exception translation.
 */
public class ControllerTest {
    private Controller controller;

    @BeforeEach
    public void setUp() {
        RepairOrderRegistry.resetForTest();

        CustomerRegistry customers = new CustomerRegistry();
        RepairOrderRegistry repairOrders = RepairOrderRegistry.getInstance();
        Printer printer = new SilentPrinter();

        controller = new Controller(customers, repairOrders, printer);
        }

    @Test
    public void controllerShouldFindRegisteredCustomerByPhoneNumber() throws Exception {
        String registeredCustomerPhone = "0737654321";

        CustomerDetailsDTO customerDetails = controller.findCustomer(registeredCustomerPhone);

        assertNotNull(customerDetails,
                "The registered customer should be found.");
        assertEquals("Cristiano Ronaldo", customerDetails.getName(),
                "The controller should return the correct customer name.");
        assertEquals("CR7@mail.com", customerDetails.getEmail(),
                "The controller should return the correct customer email.");
        assertEquals(registeredCustomerPhone, customerDetails.getPhone(),
                "The controller should return the searched phone number.");
    }

    @Test
    public void controllerShouldThrowExceptionForUnknownCustomerPhone() {
        CustomerNotFoundException thrown = assertThrows(CustomerNotFoundException.class,
                () -> controller.findCustomer("0000000000"),
                "An unknown phone number should throw CustomerNotFoundException.");

        assertTrue(thrown.getMessage().contains("0000000000"),
                "The exception message should include the missing phone number.");
    }

    @Test
    public void controllerShouldWrapDatabaseFailureAtCorrectAbstractionLevel() {
        OperationFailedException thrown = assertThrows(OperationFailedException.class,
                () -> controller.findCustomer(CustomerRegistry.DATABASE_FAILURE_PHONE_NUMBER),
                "A simulated database failure should be translated by the controller.");

        assertTrue(thrown.getMessage().contains("Could not search"),
                "The controller exception should describe the failed operation.");
        assertNotNull(thrown.getCause(),
                "The original database exception should be saved as the cause.");
    }

    @Test
    public void creatingScenarioOrderShouldCreatePendingRO4() {
        controller.createRepairOrder("Wheel is broken", "0737654321", "RJL403");

        RepairOrderDTO createdOrder = controller.getRepairOrderInfo("RO4");

        assertNotNull(createdOrder,
                "The created repair order should exist.");
        assertEquals("RO4", createdOrder.getId(),
                "The created order should have id RO4.");
        assertEquals("Pending", createdOrder.getStatus(),
                "A new repair order should be pending.");
        assertEquals(0, createdOrder.getTotalCost(),
                "A new repair order should not have repair costs yet.");
    }

    @Test
    public void scenarioRepairTasksShouldUpdateTotalCost() {
        controller.createRepairOrder("Wheel is broken", "0737654321", "RJL403");

        controller.addRepairTask("RO4", "Replace wheel", 999);
        controller.addRepairTask("RO4", "Fix wiring", 499);

        RepairOrderDTO scenarioOrder = controller.getRepairOrderInfo("RO4");

        assertNotNull(scenarioOrder,
                "RO4 should exist after adding repair tasks.");
        assertEquals(1498, scenarioOrder.getTotalCost(),
                "The total cost should be the sum of the repair tasks.");
    }

    @Test
    public void scenarioOrderShouldBecomeAcceptedWhenAcceptedThroughController() {
        controller.createRepairOrder("Wheel is broken", "0737654321", "RJL403");

        controller.acceptRepairOrder("RO4");

        RepairOrderDTO acceptedOrder = controller.getRepairOrderInfo("RO4");

        assertEquals("Accepted", acceptedOrder.getStatus(),
                "The repair order should become accepted.");
    }

    @Test
    public void controllerShouldReturnNullForMissingRepairOrderId() {
        RepairOrderDTO missingOrder = controller.getRepairOrderInfo("RO99");

        assertNull(missingOrder,
                "A missing repair order id should return null.");
    }

    @Test
    public void registryStateShouldBeUnchangedAfterCustomerNotFoundException() {
        int ordersBefore = controller.findAllRepairOrders().length;

        assertThrows(CustomerNotFoundException.class,
                () -> controller.findCustomer("0000000000"),
                "A missing phone number must throw CustomerNotFoundException.");

        int ordersAfter = controller.findAllRepairOrders().length;
        assertEquals(ordersBefore, ordersAfter,
                "Throwing CustomerNotFoundException must not change observable system state.");
    }

    @Test
    public void registryStateShouldBeUnchangedAfterOperationFailedException() {
        int ordersBefore = controller.findAllRepairOrders().length;

        assertThrows(OperationFailedException.class,
                () -> controller.findCustomer(CustomerRegistry.DATABASE_FAILURE_PHONE_NUMBER),
                "A simulated database failure must surface as OperationFailedException.");

        int ordersAfter = controller.findAllRepairOrders().length;
        assertEquals(ordersBefore, ordersAfter,
                "Throwing OperationFailedException must not change observable system state.");
    }

    private static class SilentPrinter extends Printer {
        @Override
        public void printRepairOrder(RepairOrder repairOrder) {
        }
    }
}
