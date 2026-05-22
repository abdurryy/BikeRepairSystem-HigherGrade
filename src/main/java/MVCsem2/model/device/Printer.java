package MVCsem2.model.device;

import MVCsem2.model.entity.RepairOrder;

/**
 * Prints repair orders.
 */
public class Printer {

    /**
     * Prints a repair order to System.out.
     *
     * @param repairOrder The repair order to print.
     */
    public void printRepairOrder(RepairOrder repairOrder) {
        System.out.println("Repair order id: " + repairOrder.getRepairOrderId());
        System.out.println("Problem: " + repairOrder.getProblemDescription());
        System.out.println("Customer phone number: " + repairOrder.getCustomerPhone());
        System.out.println("Bike serial number: " + repairOrder.getBikeSerialNo());
        System.out.println("Status: " + repairOrder.getStatus());
        System.out.println("Total cost: " + repairOrder.getTotalCost() + " kr");
    }
}