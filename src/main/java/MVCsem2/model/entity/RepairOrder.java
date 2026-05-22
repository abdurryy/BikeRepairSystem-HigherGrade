package MVCsem2.model.entity;

import java.util.ArrayList;
import java.util.List;

import MVCsem2.model.dto.RepairOrderDTO;
import MVCsem2.model.observer.RepairOrderObserver;
/**
 * Represents a repair order for an electric bicycle.
 * This class is the observed object in the Observer pattern and uses the
 * Strategy pattern to calculate discounts on the repair cost.
 */
public class RepairOrder {
    private String repairOrderId;
    private String problemDescription;
    private String customerPhone;
    private String bikeSerialNo;
    private DiagnosticReport diagnosticReport;
    private List<String> repairTasks;
    private List<Integer> repairTaskCosts;
    private Status status;
    private List<RepairOrderObserver> observers;
    private DiscountStrategy discountStrategy;

    private enum Status {
        PENDING,
        ACCEPTED,
        REJECTED
    }

    /**
     * Creates a new repair order with the specified information.
     * The repair order starts with status pending and a default discount strategy
     * that does not change the total cost.
     *
     * @param repairOrderId The repair order id.
     * @param problemDescription The customer's problem description.
     * @param customerPhone The customer's phone number.
     * @param bikeSerialNo The bike serial number.
     */
    public RepairOrder(String repairOrderId, String problemDescription, String customerPhone, String bikeSerialNo) {
        this.repairOrderId = valueOrEmpty(repairOrderId);
        this.problemDescription = valueOrEmpty(problemDescription);
        this.customerPhone = valueOrEmpty(customerPhone);
        this.bikeSerialNo = valueOrEmpty(bikeSerialNo);
        this.diagnosticReport = new DiagnosticReport();
        this.repairTasks = new ArrayList<>();
        this.repairTaskCosts = new ArrayList<>();
        this.status = Status.PENDING;
        this.observers = new ArrayList<>();
        this.discountStrategy = (totalCost) -> totalCost;
    }

    /**
     * Adds an observer that shall be notified when this repair order is updated.
     *
     * @param observer The observer to add.
     */
    public void addObserver(RepairOrderObserver observer) {
        if (observer != null) {
            observers.add(observer);
        }
    }

    /**
     * Notifies all registered observers about the current state of this repair order.
     */
    private void notifyObservers() {
        RepairOrderDTO orderData = new RepairOrderDTO(this);
        for (RepairOrderObserver observer : observers) {
            observer.update(orderData);
        }
    }

    /**
     * Sets the discount strategy used when calculating the total cost.
     *
     * @param discountStrategy The discount strategy to use.
     */
    public void setDiscountStrategy(DiscountStrategy discountStrategy) {
        if (discountStrategy != null) {
            this.discountStrategy = discountStrategy;
        }
    }

    /**
     * Adds a diagnostic result to this repair order and notifies observers.
     *
     * @param diagTaskResult The diagnostic result to add.
     */
    public void addDiagnosticResult(String diagTaskResult) {
        diagnosticReport.addDiagnosticResult(diagTaskResult);
        notifyObservers();
    }

    /**
     * Adds a repair task and its cost to this repair order.
     * Observers are notified after the repair task has been added.
     *
     * @param repairTask The repair task description.
     * @param cost The cost of the repair task.
     */
    public void addRepairTask(String repairTask, int cost) {
        if (repairTask == null || repairTask.trim().isEmpty() || cost < 0) {
            return;
        }
        repairTasks.add(repairTask);
        repairTaskCosts.add(cost);
        notifyObservers();
    }

    /**
     * Marks this repair order as accepted and notifies observers.
     */
    public void accept() {
        status = Status.ACCEPTED;
        notifyObservers();
    }

    /**
     * Marks this repair order as rejected and notifies observers.
     */
    public void reject() {
        status = Status.REJECTED;
        notifyObservers();
    }

    /**
     * Calculates the total cost using the current discount strategy.
     *
     * @return The total cost after applying the selected discount strategy.
     */
    public int getTotalCost() {
        return discountStrategy.applyDiscount(sumOfRepairTaskCosts());
    }

    private int sumOfRepairTaskCosts() {
        int totalCost = 0;
        for (Integer taskCost : repairTaskCosts) {
            totalCost += taskCost;
        }
        return totalCost;
    }

    /**
     * @return The repair order id.
     */
    public String getRepairOrderId() {
        return repairOrderId;
    }

    /**
     * @return The customer's problem description.
     */
    public String getProblemDescription() {
        return problemDescription;
    }

    /**
     * @return The customer's phone number.
     */
    public String getCustomerPhone() {
        return customerPhone;
    }

    /**
     * @return The bike serial number.
     */
    public String getBikeSerialNo() {
        return bikeSerialNo;
    }

    /**
     * @return The diagnostic results for this repair order.
     */
    public List<String> getDiagnosticResults() {
        return diagnosticReport.getDiagnosticResults();
    }

    /**
     * @return A copy of the repair tasks for this repair order.
     */
    public List<String> getRepairTasks() {
        return new ArrayList<>(repairTasks);
    }

    /**
     * @return True if this repair order is accepted, otherwise false.
     */
    public boolean isAccepted() {
        return status == Status.ACCEPTED;
    }

    /**
     * @return True if this repair order is rejected, otherwise false.
     */
    public boolean isRejected() {
        return status == Status.REJECTED;
    }

    /**
     * @return The current status of this repair order as text.
     */
    public String getStatus() {
        if (status == Status.ACCEPTED) {
            return "Accepted";
        }
        if (status == Status.REJECTED) {
            return "Rejected";
        }
        return "Pending";
    }

    /**
     * Converts a null string to an empty string.
     *
     * @param value The string to check.
     * @return The original string, or an empty string if the original string was null.
     */
    private String valueOrEmpty(String value) {
        return (value == null) ? "" : value;
    }
}