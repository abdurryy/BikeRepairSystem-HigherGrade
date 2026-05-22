package MVCsem2.model.dto;

import MVCsem2.model.entity.RepairOrder;
import java.util.Collections;
import java.util.List;

/**
 * Carries all information about a repair order that needs to be exposed across
 * layer boundaries. This is an immutable snapshot of the underlying entity so
 * that the entity itself is never leaked to the view or to observers.
 */
public class RepairOrderDTO {
    private final String id;
    private final String status;
    private final int totalCost;
    private final List<String> tasks;

    /**
     * Creates a new instance based on a repair order entity.
     *
     * @param order The entity that data is extracted from.
     */
    public RepairOrderDTO(RepairOrder order) {
        this.id = order.getRepairOrderId();
        this.status = order.getStatus();
        this.totalCost = order.getTotalCost();
        this.tasks = Collections.unmodifiableList(order.getRepairTasks());
    }

    /**
     * Creates a repair order DTO array from a repair order array.
     *
     * @param repairList The repair orders that will be converted.
     * @return An array with repair order DTOs.
     */
    public static RepairOrderDTO[] createRepairOrderDTO(RepairOrder[] repairList) {
        RepairOrderDTO[] repairOrderDTOs = new RepairOrderDTO[repairList.length];
        for (int i = 0; i < repairList.length; i++) {
            repairOrderDTOs[i] = new RepairOrderDTO(repairList[i]);
        }
        return repairOrderDTOs;
    }

    /**
     * @return The repair order id.
     */
    public String getId() {
        return id;
    }

    /**
     * @return The repair order status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return The total cost.
     */
    public int getTotalCost() {
        return totalCost;
    }

    /**
     * @return The repair tasks. The returned list is unmodifiable.
     */
    public List<String> getTasks() {
        return tasks;
    }
}
