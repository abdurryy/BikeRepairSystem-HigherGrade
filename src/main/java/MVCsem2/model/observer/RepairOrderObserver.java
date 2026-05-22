package MVCsem2.model.observer;

import MVCsem2.model.dto.RepairOrderDTO;

/**
 * Interface for observers that want to be notified about repair order updates.
 * Any class implementing this interface can subscribe to updates from a RepairOrder.
 */
public interface RepairOrderObserver {
    /**
     * Invoked when a repair order has been updated.
     *
     * @param updatedOrder A DTO representing the state of the updated repair order.
     */
    void update(RepairOrderDTO updatedOrder);
}
