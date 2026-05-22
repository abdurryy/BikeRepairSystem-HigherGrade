package MVCsem2.model.observer;

import MVCsem2.model.dto.RepairOrderDTO;
import MVCsem2.model.entity.RepairOrder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the template method flow used by repair order observers.
 */
public class AbstractRepairOrderObserverTest {
    @Test
    public void updateShouldCallConcreteUpdateHandling() {
        RepairOrderDTO order = createOrderDTO();
        RecordingObserver observer = new RecordingObserver(false);

        observer.update(order);

        assertSame(order, observer.handledOrder,
                "The template method should make the updated order available to the concrete step.");
        assertNull(observer.handledError,
                "No error should be handled when the concrete step succeeds.");
    }

    @Test
    public void updateShouldHandleErrorsFromConcreteUpdateHandling() {
        RecordingObserver observer = new RecordingObserver(true);

        observer.update(createOrderDTO());

        assertNotNull(observer.handledError,
                "The template method should pass errors from the concrete step to the error handler.");
        assertEquals("Forced update failure", observer.handledError.getMessage(),
                "The original error message should be kept.");
    }

    private RepairOrderDTO createOrderDTO() {
        return new RepairOrderDTO(new RepairOrder("RO7", "Battery check", "0701112233", "BIKE700"));
    }

    private static class RecordingObserver extends AbstractRepairOrderObserver {
        private final boolean shouldFail;
        private RepairOrderDTO handledOrder;
        private Exception handledError;

        RecordingObserver(boolean shouldFail) {
            this.shouldFail = shouldFail;
        }

        @Override
        protected void doHandleRepairOrderUpdate() throws Exception {
            if (shouldFail) {
                throw new Exception("Forced update failure");
            }
            handledOrder = getUpdatedOrder();
        }

        @Override
        protected void handleErrors(Exception e) {
            handledError = e;
        }
    }
}
