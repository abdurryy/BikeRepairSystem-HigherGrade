package MVCsem2.integration.register;

import MVCsem2.model.entity.RepairOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the repair order registry.
 */
public class RepairOrderRegistryTest {
    private RepairOrderRegistry registry;

    @BeforeEach
    public void setUp() {
        RepairOrderRegistry.resetForTest();
        registry = RepairOrderRegistry.getInstance();
    }

    @Test
    public void registryShouldContainTheHardcodedOrdersAtStartup() {
        RepairOrder[] ordersInRegistry = registry.findAllRepairOrders();

        assertEquals(3, ordersInRegistry.length,
                "The registry should contain the three hardcoded repair orders at startup.");
    }

    @Test
    public void registeredCustomerShouldHaveTheHardcodedRO2RepairOrder() {
        String registeredCustomerPhone = "0737654321";

        RepairOrder registeredCustomerOrder = registry.findRepairOrder(registeredCustomerPhone);

        assertNotNull(registeredCustomerOrder,
                "The hardcoded repair order should be found.");
        assertEquals("RO2", registeredCustomerOrder.getRepairOrderId(),
                "The hardcoded repair order for this phone number should be RO2.");
        assertEquals("Brakes need adjustment", registeredCustomerOrder.getProblemDescription(),
                "The hardcoded problem description for RO2 should be returned.");
        assertEquals("BIKE200", registeredCustomerOrder.getBikeSerialNo(),
                "The hardcoded bike serial number for RO2 should be returned.");
    }

    @Test
    public void ro2ShouldBePossibleToFindByRepairOrderId() {
        RepairOrder order = registry.getRepairOrder("RO2");

        assertNotNull(order,
                "RO2 should exist in the hardcoded registry.");
        assertEquals("Brakes need adjustment", order.getProblemDescription(),
                "RO2 should contain the hardcoded brakes problem.");
    }

    @Test
    public void missingRepairOrderIdShouldReturnNull() {
        RepairOrder missingOrder = registry.getRepairOrder("RO99");

        assertNull(missingOrder,
                "A repair order id that does not exist should return null.");
    }

    @Test
    public void creatingViewScenarioOrderShouldStoreNewRepairOrder() {
        String problem = "Wheel is broken";
        String registeredCustomerPhone = "0737654321";
        String bikeSerialNo = "RJL403";

        registry.createRepairOrder(problem, registeredCustomerPhone, bikeSerialNo);

        RepairOrder createdOrder = registry.findRepairOrder(registeredCustomerPhone);

        assertNotNull(createdOrder,
                "The newly created scenario repair order should exist.");
        assertTrue(createdOrder.getRepairOrderId().startsWith("RO"),
                "The created repair order should get an id starting with RO.");
        assertEquals(problem, createdOrder.getProblemDescription(),
                "The created order should store the scenario problem description.");
        assertEquals(registeredCustomerPhone, createdOrder.getCustomerPhone(),
                "The created order should store the customer's phone number.");
        assertEquals(bikeSerialNo, createdOrder.getBikeSerialNo(),
                "The created order should store the bike serial number from the scenario.");
    }

    @Test
    public void creatingScenarioOrderShouldIncreaseRegistrySize() {
        registry.createRepairOrder("Wheel is broken", "0737654321", "RJL403");

        RepairOrder[] ordersAfterCreate = registry.findAllRepairOrders();

        assertEquals(4, ordersAfterCreate.length,
                "The registry should contain four repair orders after creating one new order.");
    }

    @Test
    public void latestRegisteredCustomerOrderShouldBeTheNewOrderAfterScenarioOrderIsCreated() {
        String registeredCustomerPhone = "0737654321";
        String problem = "Wheel is broken";

        registry.createRepairOrder(problem, registeredCustomerPhone, "RJL403");

        RepairOrder latestOrder = registry.findRepairOrder(registeredCustomerPhone);

        assertNotNull(latestOrder,
                "A repair order should be found for the customer.");
        assertEquals(problem, latestOrder.getProblemDescription(),
                "The latest repair order should be the newly created order.");
    }

    @Test
    public void updatedRepairOrderShouldKeepAcceptedStatus() {
        RepairOrder orderToUpdate = registry.getRepairOrder("RO2");
        orderToUpdate.accept();

        registry.updateRepairOrder(orderToUpdate);

        RepairOrder storedOrder = registry.getRepairOrder("RO2");

        assertEquals("Accepted", storedOrder.getStatus(),
                "The registry should keep the updated accepted status.");
    }

    @Test
    public void findAllRepairOrdersShouldReturnDefensiveCopy() {
        RepairOrder[] firstSnapshot = registry.findAllRepairOrders();
        int originalSize = firstSnapshot.length;

        firstSnapshot[0] = null;

        RepairOrder[] secondSnapshot = registry.findAllRepairOrders();
        assertEquals(originalSize, secondSnapshot.length,
                "The registry size must not change just because a caller mutated its returned array.");
        assertNotNull(secondSnapshot[0],
                "Mutating the returned array must not affect the registry's internal data.");
    }
}