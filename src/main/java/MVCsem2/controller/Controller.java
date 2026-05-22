package MVCsem2.controller;

import MVCsem2.controller.exception.OperationFailedException;
import MVCsem2.integration.exception.CustomerNotFoundException;
import MVCsem2.integration.exception.DatabaseFailureException;
import MVCsem2.integration.register.CustomerRegistry;
import MVCsem2.integration.register.RepairOrderRegistry;
import MVCsem2.model.device.Printer;
import MVCsem2.model.dto.CustomerDetailsDTO;
import MVCsem2.model.dto.RepairOrderDTO;
import MVCsem2.model.entity.Customer;
import MVCsem2.model.entity.RepairOrder;
import MVCsem2.model.observer.RepairOrderObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles interaction between the view and the rest of the system.
 */
public class Controller {
    private Printer printer;
    private CustomerRegistry customerRegistry;
    private RepairOrderRegistry repairOrderRegistry;
    private List<RepairOrderObserver> repairOrderObservers = new ArrayList<>();

    /**
     * Creates a controller with the specified registries and printer.
     *
     * @param customerRegistry The customer registry.
     * @param repairOrderRegistry The repair order registry.
     * @param printer The printer used to print repair orders.
     */
    public Controller(CustomerRegistry customerRegistry, RepairOrderRegistry repairOrderRegistry, Printer printer) {
        this.customerRegistry = customerRegistry;
        this.repairOrderRegistry = repairOrderRegistry;
        this.printer = printer;
    }

    /**
     * Adds an observer that will be attached to newly created repair orders.
     *
     * @param obs The observer to add.
     */
    public void addRepairOrderObserver(RepairOrderObserver obs) {
        if (obs != null) {
            repairOrderObservers.add(obs);
        }
    }

    /**
     * Finds customer details using a phone number.
     *
     * @param phoneNumber The customer's phone number.
     * @return The found customer details.
     * @throws CustomerNotFoundException If no customer with the specified phone number exists.
     * @throws OperationFailedException If the customer registry cannot be reached.
     */
    public CustomerDetailsDTO findCustomer(String phoneNumber)
            throws CustomerNotFoundException, OperationFailedException {
        try {
            Customer customer = customerRegistry.findCustomer(phoneNumber);
            return new CustomerDetailsDTO(customer);
        } catch (DatabaseFailureException dbFailure) {
            throw new OperationFailedException("Could not search for customer.", dbFailure);
        }
    }

    /**
     * Creates a new repair order and attaches all registered observers to it.
     *
     * @param problemDescr The customer's problem description.
     * @param customerPhone The customer's phone number.
     * @param bikeSerialNo The bike serial number.
     */
    public void createRepairOrder(String problemDescr, String customerPhone, String bikeSerialNo) {
        repairOrderRegistry.createRepairOrder(problemDescr, customerPhone, bikeSerialNo);

        RepairOrder newOrder = repairOrderRegistry.findRepairOrder(customerPhone);
        if (newOrder != null) {
            for (RepairOrderObserver obs : repairOrderObservers) {
                newOrder.addObserver(obs);
            }
        }
    }

    /**
     * Adds a repair task to a repair order.
     *
     * @param repairOrderId The repair order id.
     * @param repairTask The repair task description.
     * @param cost The repair task cost.
     */
    public void addRepairTask(String repairOrderId, String repairTask, int cost) {
        RepairOrder repairOrder = repairOrderRegistry.getRepairOrder(repairOrderId);
        if (repairOrder != null) {
            repairOrder.addRepairTask(repairTask, cost);
        }
    }

    /**
     * Adds a diagnostic result to a repair order.
     *
     * @param repairOrderId The repair order id.
     * @param diagnosticResult The diagnostic result.
     */
    public void addDiagnosticResult(String repairOrderId, String diagnosticResult) {
        RepairOrder repairOrder = repairOrderRegistry.getRepairOrder(repairOrderId);
        if (repairOrder != null) {
            repairOrder.addDiagnosticResult(diagnosticResult);
        }
    }

    /**
     * Accepts a repair order and prints it.
     *
     * @param repairOrderId The repair order id.
     */
    public void acceptRepairOrder(String repairOrderId) {
        RepairOrder repairOrder = repairOrderRegistry.getRepairOrder(repairOrderId);
        if (repairOrder != null) {
            repairOrder.accept();
            repairOrderRegistry.updateRepairOrder(repairOrder);
            printer.printRepairOrder(repairOrder);
        }
    }

    /**
     * Rejects a repair order.
     *
     * @param repairOrderId The repair order id.
     */
    public void rejectRepairOrder(String repairOrderId) {
        RepairOrder repairOrder = repairOrderRegistry.getRepairOrder(repairOrderId);
        if (repairOrder != null) {
            repairOrder.reject();
            repairOrderRegistry.updateRepairOrder(repairOrder);
        }
    }

    /**
     * Finds all repair orders.
     *
     * @return All repair orders as DTOs.
     */
    public RepairOrderDTO[] findAllRepairOrders() {
        RepairOrder[] repairList = repairOrderRegistry.findAllRepairOrders();
        return RepairOrderDTO.createRepairOrderDTO(repairList);
    }

    /**
     * Finds the latest repair order connected to a customer phone number.
     *
     * @param phoneNumber The customer's phone number.
     * @return The repair order as a DTO, or null if no order was found.
     */
    public RepairOrderDTO findRepairOrder(String phoneNumber) {
        RepairOrder repairOrder = repairOrderRegistry.findRepairOrder(phoneNumber);
        if (repairOrder == null) {
            return null;
        }
        return new RepairOrderDTO(repairOrder);
    }

    /**
     * Gets information about a repair order.
     *
     * @param repairOrderId The repair order id.
     * @return The repair order as a DTO, or null if no order was found.
     */
    public RepairOrderDTO getRepairOrderInfo(String repairOrderId) {
        RepairOrder repairOrder = repairOrderRegistry.getRepairOrder(repairOrderId);
        if (repairOrder == null) {
            return null;
        }
        return new RepairOrderDTO(repairOrder);
    }
}