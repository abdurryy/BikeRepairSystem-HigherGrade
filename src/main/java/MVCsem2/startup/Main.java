package MVCsem2.startup;

import MVCsem2.controller.Controller;
import MVCsem2.integration.register.CustomerRegistry;
import MVCsem2.integration.register.RepairOrderRegistry;
import MVCsem2.model.device.Printer;
import MVCsem2.util.log.FileLogger;
import MVCsem2.util.log.Logger;
import MVCsem2.view.RepairOrderLogger;
import MVCsem2.view.RepairOrderView;
import MVCsem2.view.View;

/**
 * Starts the application and initializes core components and design patterns.
 */
public class Main {

    /**
     * The main method used to start the system.
     */
    public static void main(String[] args) {
        // 1. Singleton pattern: access the single registry instance.
        RepairOrderRegistry repairOrderRegistry = RepairOrderRegistry.getInstance();

        CustomerRegistry customerRegistry = new CustomerRegistry();
        Printer printer = new Printer();

        // 2. Initialize the controller.
        Controller controller = new Controller(customerRegistry, repairOrderRegistry, printer);

        // 3. Observer pattern: register one observer that prints to System.out and
        //    another that writes to a dedicated repair-order log file. Both observers
        //    are wired up with their dependencies here, in the composition root.
        Logger repairOrderLog = new FileLogger("repair-order-log.txt");
        controller.addRepairOrderObserver(new RepairOrderView());
        controller.addRepairOrderObserver(new RepairOrderLogger(repairOrderLog));

        // 4. Start the UI. The view uses a separate logger for developer error reports.
        Logger errorLog = new FileLogger();
        View view = new View(controller, errorLog);
        view.start();
    }
}
