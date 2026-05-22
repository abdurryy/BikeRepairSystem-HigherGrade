package MVCsem2.integration.register;

import MVCsem2.integration.exception.CustomerNotFoundException;
import MVCsem2.integration.exception.DatabaseFailureException;
import MVCsem2.model.entity.Bike;
import MVCsem2.model.entity.Customer;

/**
 * Handles stored customer data.
 */
public class CustomerRegistry {
    /**
     * Searching for this phone number simulates a failed database call.
     */
    public static final String DATABASE_FAILURE_PHONE_NUMBER = "0999999999";

    private final Customer[] customers;

    /**
     * Creates a customer registry with hardcoded data since there is no real database.
     */
    public CustomerRegistry() {
        customers = new Customer[] {
            new Customer("Ahmed Adam", "ahmed@mail.com", "0701234567",
                    new Bike("bmx", "Reaction Hybrid", "CUBE123")),
            new Customer("Cristiano Ronaldo", "CR7@mail.com", "0737654321",
                    new Bike("Scotter", "1", "SET401")),
            new Customer("Lionel Messi", "LM10@mail.com", "0761112233",
                    new Bike("E-bike", "1", "LOE230"))
        };
    }

    /**
     * Finds a customer by phone number.
     *
     * @param phoneNumber The phone number used to identify the customer.
     * @return The customer with the given phone number.
     * @throws CustomerNotFoundException If no customer has the specified phone number.
     * @throws DatabaseFailureException If the simulated database call fails. This is an
     *         unchecked exception because a database outage cannot meaningfully be
     *         recovered from at the integration layer.
     */
    public Customer findCustomer(String phoneNumber) throws CustomerNotFoundException {
        if (DATABASE_FAILURE_PHONE_NUMBER.equals(phoneNumber)) {
            throw new DatabaseFailureException(phoneNumber);
        }

        for (Customer customer : customers) {
            if (customer.getPhoneNumber().equals(phoneNumber)) {
                return customer;
            }
        }

        throw new CustomerNotFoundException(phoneNumber);
    }
}