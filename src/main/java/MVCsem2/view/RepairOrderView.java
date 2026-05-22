package MVCsem2.view;

import MVCsem2.model.dto.RepairOrderDTO;
import MVCsem2.model.observer.AbstractRepairOrderObserver;

/**
 * A view that prints repair order updates to System.out for technicians 
 * and receptionists to see.
 */
public class RepairOrderView extends AbstractRepairOrderObserver {
    @Override
    protected void doHandleRepairOrderUpdate() {
        RepairOrderDTO updatedOrder = getUpdatedOrder();
        System.out.println("\n[NOTIFICATION - SYSTEM UPDATE]");
        System.out.println("Repair Order ID: " + updatedOrder.getId());
        System.out.println("Current Status:  " + updatedOrder.getStatus());
        System.out.println("Total Cost:      " + updatedOrder.getTotalCost() + " kr");
        System.out.println("-------------------------------\n");
    }

    @Override
    protected void handleErrors(Exception e) {
        throw new IllegalStateException("The repair order update could not be shown.", e);
    }
}
