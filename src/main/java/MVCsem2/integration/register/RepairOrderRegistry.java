package MVCsem2.integration.register;

import MVCsem2.model.entity.RepairOrder;

/**
 * A singleton registry that handles access to stored repair orders.
 */
public class RepairOrderRegistry {
    private static final RepairOrderRegistry INSTANCE = new RepairOrderRegistry();
    private RepairOrder[] repairOrders;

    /**
     * Creates the singleton repair order registry with hardcoded initial data.
     * The constructor is private because this class implements the Singleton pattern.
     */
    private RepairOrderRegistry() {
        loadDefaultRepairOrders();
    }

    /**
     * @return The single instance of this registry.
     */
    public static RepairOrderRegistry getInstance() {
        return INSTANCE;
    }

    /**
     * Test fixture helper that resets the singleton instance to its original
     * hardcoded state. Application code must not call this method; it exists
     * only so that unit tests can run independently of each other despite the
     * shared singleton state.
     */
    public static void resetForTest() {
        INSTANCE.loadDefaultRepairOrders();
    }

    private void loadDefaultRepairOrders() {
        repairOrders = new RepairOrder[] {
            new RepairOrder("RO1", "Routine check", "0701234567", "BIKE100"),
            new RepairOrder("RO2", "Brakes need adjustment", "0737654321", "BIKE200"),
            new RepairOrder("RO3", "Motor makes strange noise", "0761112233", "BIKE300")
        };
    }

    /**
     * Finds a repair order using its repair order id.
     *
     * @param repairOrderId The repair order id.
     * @return The matching repair order, or null if no match was found.
     */
    public RepairOrder getRepairOrder(String repairOrderId) {
        for (RepairOrder order : repairOrders) {
            if (order.getRepairOrderId().equals(repairOrderId)) {
                return order;
            }
        }
        return null;
    }

    /**
     * Returns all stored repair orders.
     * A defensive copy of the internal array is returned so that callers cannot
     * mutate the registry's backing store.
     *
     * @return A defensive copy of all stored repair orders.
     */
    public RepairOrder[] findAllRepairOrders() {
        RepairOrder[] copy = new RepairOrder[repairOrders.length];
        System.arraycopy(repairOrders, 0, copy, 0, repairOrders.length);
        return copy;
    }

    /**
     * Stores a new repair order in the registry.
     *
     * @param problemDescr The customer's problem description.
     * @param customerPhone The customer's phone number.
     * @param bikeSerialNo The bike serial number.
     */
    public void createRepairOrder(String problemDescr, String customerPhone, String bikeSerialNo) {
        String repairOrderId = "RO" + (repairOrders.length + 1);
        RepairOrder newRepairOrder = new RepairOrder(repairOrderId, problemDescr, customerPhone, bikeSerialNo);

        RepairOrder[] updatedRepairOrders = new RepairOrder[repairOrders.length + 1];
        System.arraycopy(repairOrders, 0, updatedRepairOrders, 0, repairOrders.length);
        updatedRepairOrders[updatedRepairOrders.length - 1] = newRepairOrder;

        repairOrders = updatedRepairOrders;
    }

    /**
     * Updates an existing repair order in the registry.
     *
     * @param repairOrder The repair order with updated information.
     */
    public void updateRepairOrder(RepairOrder repairOrder) {
        for (int i = 0; i < repairOrders.length; i++) {
            if (repairOrders[i].getRepairOrderId().equals(repairOrder.getRepairOrderId())) {
                repairOrders[i] = repairOrder;
                return;
            }
        }
    }

    /**
     * Finds the latest repair order connected to the specified customer phone number.
     *
     * @param phoneNumber The customer's phone number.
     * @return The latest matching repair order, or null if no match was found.
     */
    public RepairOrder findRepairOrder(String phoneNumber) {
        for (int i = repairOrders.length - 1; i >= 0; i--) {
            if (repairOrders[i].getCustomerPhone().equals(phoneNumber)) {
                return repairOrders[i];
            }
        }
        return null;
    }
}