package MVCsem2.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RepairOrderTest {
    private RepairOrder repairOrderInScenario;

    @BeforeEach
    public void setUp() {

        repairOrderInScenario = new RepairOrder("RO4", "Wheel is broken", "0737654321", "RJL403");
    }

    @Test
    public void scenarioOrderShouldBeCreatedWithPendingStatus() {

        assertEquals("Pending", repairOrderInScenario.getStatus(),
                "The repair order should be pending when it is first created.");
    }

    @Test
    public void scenarioOrderShouldStoreCustomerAndBikeInformation() {

        String expectedCustomerPhone = "0737654321";
        String expectedBikeSerialNo = "RJL403";

        assertEquals(expectedCustomerPhone, repairOrderInScenario.getCustomerPhone(),
                "The customer phone number should be stored in the repair order.");
        assertEquals(expectedBikeSerialNo, repairOrderInScenario.getBikeSerialNo(),
                "The bike serial number should be stored in the repair order.");
    }

    @Test
    public void addedDiagnosticResultsShouldRemainInCorrectOrder() {

        String damagedWheel = "Wheel is damaged";
        String brokenHeadlights = "Headlights are broken";

        repairOrderInScenario.addDiagnosticResult(damagedWheel);
        repairOrderInScenario.addDiagnosticResult(brokenHeadlights);

        assertEquals(damagedWheel, repairOrderInScenario.getDiagnosticResults().get(0),
                "The first diagnostic result should describe the damaged wheel.");
        assertEquals(brokenHeadlights, repairOrderInScenario.getDiagnosticResults().get(1),
                "The second diagnostic result should describe the broken headlights.");
    }

    @Test
    public void addedRepairTasksShouldRemainInCorrectOrder() {

        String replaceWheelTask = "Replace wheel";
        String fixWiringTask = "Fix wiring";

        repairOrderInScenario.addRepairTask(replaceWheelTask, 999);
        repairOrderInScenario.addRepairTask(fixWiringTask, 499);

        assertEquals(replaceWheelTask, repairOrderInScenario.getRepairTasks().get(0),
                "The first repair task should be to replace the wheel.");
        assertEquals(fixWiringTask, repairOrderInScenario.getRepairTasks().get(1),
                "The second repair task should be to fix the wiring.");
    }

    @Test
    public void totalCostShouldIncludeAllScenarioRepairTasks() {

        int replaceWheelPrice = 999;
        int fixWiringPrice = 499;
        int expectedTotalCost = replaceWheelPrice + fixWiringPrice;

        repairOrderInScenario.addRepairTask("Replace wheel", replaceWheelPrice);
        repairOrderInScenario.addRepairTask("Fix wiring", fixWiringPrice);

        assertEquals(expectedTotalCost, repairOrderInScenario.getTotalCost(),
                "The total cost should include both repair tasks.");
    }

    @Test
    public void acceptedOrderShouldReportAcceptedStatus() {

        repairOrderInScenario.accept();

        assertTrue(repairOrderInScenario.isAccepted(),
                "The repair order should report that it is accepted.");
        assertEquals("Accepted", repairOrderInScenario.getStatus(),
                "The repair order status should be Accepted.");
    }

    @Test
    public void rejectedOrderShouldReportRejectedStatus() {

        repairOrderInScenario.reject();

        assertTrue(repairOrderInScenario.isRejected(),
                "The repair order should report that it is rejected.");
        assertEquals("Rejected", repairOrderInScenario.getStatus(),
                "The repair order status should be Rejected.");
    }

    @Test
    public void winterDiscountShouldReduceTotalCostByTwentyPercent() {
        repairOrderInScenario.addRepairTask("Replace wheel", 1000);
        repairOrderInScenario.setDiscountStrategy(new WinterDiscount());
        assertEquals(800, repairOrderInScenario.getTotalCost(),
            "WinterDiscount should reduce the total cost by 20 percent.");

    }

    @Test
    public void observerShouldBeNotifiedWhenRepairOrderIsUpdated() {
        boolean[] wasNotified = {false};
        String[] notifiedId = {null};
        repairOrderInScenario.addObserver(updatedOrder -> {
            wasNotified[0] = true;
            notifiedId[0] = updatedOrder.getId();
        });

        repairOrderInScenario.accept();

        assertTrue(wasNotified[0],
                "Observers should be notified when the repair order is updated.");
        assertEquals("RO4", notifiedId[0],
                "The DTO delivered to the observer should describe the updated repair order.");
    }
}