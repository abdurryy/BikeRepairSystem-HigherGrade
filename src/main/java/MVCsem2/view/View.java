package MVCsem2.view;

import MVCsem2.controller.Controller;
import MVCsem2.controller.exception.OperationFailedException;
import MVCsem2.integration.exception.CustomerNotFoundException;
import MVCsem2.integration.register.CustomerRegistry;
import MVCsem2.model.dto.CustomerDetailsDTO;
import MVCsem2.model.dto.RepairOrderDTO;
import MVCsem2.util.log.Logger;

/**
 * Handles the user interface.
 */
public class View {
    private Controller controller;
    private Logger logger;

    /**
     * Creates a view with the specified controller and logger.
     *
     * @param controller The controller used by this view.
     * @param logger The logger used for developer error reports.
     */
    public View(Controller controller, Logger logger) {
        this.controller = controller;
        this.logger = logger;
    }

    /**
     * Starts a hardcoded execution of the program.
     */
    public void start() {
        findAndPrintCustomer("0737654321");
        findAndPrintCustomer("0700000000");
        findAndPrintCustomer(CustomerRegistry.DATABASE_FAILURE_PHONE_NUMBER);

        String problemDescr = "Wheel is broken";
        String customerPhone = "0737654321";
        String bikeSerialNo = "RJL403";
        controller.createRepairOrder(problemDescr, customerPhone, bikeSerialNo);

        RepairOrderDTO[] repairOrders = controller.findAllRepairOrders();

        System.out.println("All repair orders:");

        for (RepairOrderDTO repairOrder : repairOrders) {
            System.out.println("Repair order id: " + repairOrder.getId());
            System.out.println("Status: " + repairOrder.getStatus());
            System.out.println("Total cost: " + repairOrder.getTotalCost());
            System.out.println();
        }

        String phoneNumberForRepairOrder = customerPhone;
        RepairOrderDTO repairOrder = controller.findRepairOrder(phoneNumberForRepairOrder);

        if (repairOrder != null) {
            System.out.println("Repair order found before diagnostics and repair tasks:");
            printRepairOrder(repairOrder);
        } else {
            System.out.println("No repair order found for phone number: " + phoneNumberForRepairOrder);
            System.out.println();
        }

        String repairOrderId = "RO4";
        String[] diagnosticResults = {
            "Wheel is damaged",
            "Headlights are broken"
        };

        for (String diagnosticResult : diagnosticResults) {
            controller.addDiagnosticResult(repairOrderId, diagnosticResult);
        }

        String[] repairTasks = {
            "Replace wheel",
            "Fix wiring"
        };

        int[] repairTaskCosts = {
            999,
            499
        };

        for (int i = 0; i < repairTasks.length; i++) {
            controller.addRepairTask(repairOrderId, repairTasks[i], repairTaskCosts[i]);
        }

        RepairOrderDTO updatedRepairOrder = controller.getRepairOrderInfo(repairOrderId);

        if (updatedRepairOrder != null) {
            System.out.println("Repair order after diagnostics and repair tasks:");
            printRepairOrder(updatedRepairOrder);
        } else {
            System.out.println("Repair order not found: " + repairOrderId);
            System.out.println();
        }

        String repairOrderIdToDecide = "RO4";
        boolean repairOrderAccepted = true;

        if (repairOrderAccepted) {
            controller.acceptRepairOrder(repairOrderIdToDecide);
        } else {
            controller.rejectRepairOrder(repairOrderIdToDecide);
        }
    }

    private void findAndPrintCustomer(String phoneNumber) {
        try {
            CustomerDetailsDTO customerDetails = controller.findCustomer(phoneNumber);
            System.out.println("Customer found:");
            System.out.println("Name: " + customerDetails.getName());
            System.out.println("Email: " + customerDetails.getEmail());
            System.out.println("Phone: " + customerDetails.getPhone());
            System.out.println();
        } catch (CustomerNotFoundException customerNotFound) {
            System.out.println("No customer exists with phone number: " + customerNotFound.getPhoneNumber());
            System.out.println();
        } catch (OperationFailedException operationFailed) {
            System.out.println("The customer registry can not be reached. Please try again later.");
            logger.log("Customer search failed: " + operationFailed.getMessage());
            logger.log("Cause: " + operationFailed.getCause());
            System.out.println();
        }
    }

    private void printRepairOrder(RepairOrderDTO repairOrder) {
        System.out.println("Repair order id: " + repairOrder.getId());
        System.out.println("Status: " + repairOrder.getStatus());
        System.out.println("Total cost: " + repairOrder.getTotalCost());
        System.out.println();
    }
}