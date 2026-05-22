package MVCsem2.model.observer;

import MVCsem2.model.dto.RepairOrderDTO;

/**
 * Template class for observers that react to repair order updates.
 * It keeps the common error handling flow in one place while subclasses decide
 * what shall happen when an update is received.
 */
public abstract class AbstractRepairOrderObserver implements RepairOrderObserver {
    private RepairOrderDTO updatedOrder;

    /**
     * Receives an update from the observed repair order.
     *
     * @param updatedOrder The updated repair order information.
     */
    @Override
    public final void update(RepairOrderDTO updatedOrder) {
        this.updatedOrder = updatedOrder;
        handleRepairOrderUpdate();
    }

    private void handleRepairOrderUpdate() {
        try {
            doHandleRepairOrderUpdate();
        } catch (Exception e) {
            handleErrors(e);
        }
    }

    /**
     * Performs the observer specific handling of a repair order update.
     *
     * @throws Exception If the update can not be handled.
     */
    protected abstract void doHandleRepairOrderUpdate() throws Exception;

    /**
     * Handles errors thrown by the concrete update handling.
     *
     * @param e The error that was thrown.
     */
    protected abstract void handleErrors(Exception e);

    /**
     * @return The repair order data currently being handled.
     */
    protected RepairOrderDTO getUpdatedOrder() {
        return updatedOrder;
    }
}
