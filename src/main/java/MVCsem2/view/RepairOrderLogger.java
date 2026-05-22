package MVCsem2.view;

import MVCsem2.model.dto.RepairOrderDTO;
import MVCsem2.model.observer.AbstractRepairOrderObserver;
import MVCsem2.util.log.Logger;

/**
 * An observer implementation that logs updated repair orders through a
 * {@link Logger}. The destination (file, console, etc.) is supplied by the
 * caller, so this observer is independent of any concrete logging mechanism.
 */
public class RepairOrderLogger extends AbstractRepairOrderObserver {
    private final Logger logger;

    /**
     * Creates a new repair order observer that writes updates to the supplied logger.
     *
     * @param logger The logger that receives repair order updates. Must not be null.
     */
    public RepairOrderLogger(Logger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("logger must not be null");
        }
        this.logger = logger;
    }

    /**
     * Logs the updated repair order information through the configured logger.
     */
    @Override
    protected void doHandleRepairOrderUpdate() {
        RepairOrderDTO updatedOrder = getUpdatedOrder();
        StringBuilder logMsg = new StringBuilder();
        logMsg.append("Update - ID: ").append(updatedOrder.getId());
        logMsg.append(", Status: ").append(updatedOrder.getStatus());
        logMsg.append(", Total Cost: ").append(updatedOrder.getTotalCost());
        logger.log(logMsg.toString());
    }

    @Override
    protected void handleErrors(Exception e) {
        logger.log("Could not log repair order update: " + e.getMessage());
    }
}
