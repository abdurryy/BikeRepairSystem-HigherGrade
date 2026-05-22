package MVCsem2.model.dto;

import MVCsem2.model.entity.RepairOrder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RepairOrderDTOTest {

    @Test
    public void dtoShouldCopyRepairTasksAndTotalCostFromScenarioOrder() {

        RepairOrder orderWithTasks = new RepairOrder("RO4", "Wheel is broken", "0737654321", "RJL403");

        String wheelTask = "Replace wheel";
        String wiringTask = "Fix wiring";
        int wheelCost = 999;
        int wiringCost = 499;

        orderWithTasks.addRepairTask(wheelTask, wheelCost);
        orderWithTasks.addRepairTask(wiringTask, wiringCost);

        RepairOrderDTO orderDetails = new RepairOrderDTO(orderWithTasks);

        assertEquals(wheelTask, orderDetails.getTasks().get(0),
                "The first task in the DTO should be the wheel replacement.");
        assertEquals(wiringTask, orderDetails.getTasks().get(1),
                "The second task in the DTO should be the wiring repair.");
        assertEquals(wheelCost + wiringCost, orderDetails.getTotalCost(),
                "The DTO should contain the total cost of the scenario repair tasks.");
    }

    @Test
    public void dtoShouldShowAcceptedStatusAfterOrderIsAccepted() {

        RepairOrder acceptedOrder = new RepairOrder("RO4", "Wheel is broken", "0737654321", "RJL403");
        acceptedOrder.accept();

        RepairOrderDTO orderDetails = new RepairOrderDTO(acceptedOrder);

        assertEquals("Accepted", orderDetails.getStatus(),
                "The DTO should contain the updated accepted status.");
    }
}