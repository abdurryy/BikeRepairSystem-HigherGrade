package MVCsem2.integration.register;

import MVCsem2.integration.exception.CustomerNotFoundException;
import MVCsem2.integration.exception.DatabaseFailureException;
import MVCsem2.model.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests exception handling and normal search flow in CustomerRegistry.
 */
public class CustomerRegistryTest {
    private CustomerRegistry registry;

    @BeforeEach
    public void setUp() {
        registry = new CustomerRegistry();
    }

    @Test
    public void registeredCustomerShouldBeFoundByRegisteredPhoneNumber() throws Exception {
        String registeredCustomerPhone = "0737654321";

        Customer registeredCustomer = registry.findCustomer(registeredCustomerPhone);

        assertNotNull(registeredCustomer,
                "A registered customer should be found.");
        assertEquals("Cristiano Ronaldo", registeredCustomer.getName(),
                "The correct customer should be returned.");
        assertEquals("CR7@mail.com", registeredCustomer.getEmail(),
                "The customer email should match.");
        assertEquals(registeredCustomerPhone, registeredCustomer.getPhoneNumber(),
                "The phone number should match the search value.");
    }

    @Test
    public void missingPhoneNumberShouldThrowCustomerNotFoundException() {
        CustomerNotFoundException thrown = assertThrows(CustomerNotFoundException.class,
                () -> registry.findCustomer("0700000000"),
                "A missing phone number should throw CustomerNotFoundException.");

        assertEquals("0700000000", thrown.getPhoneNumber(),
                "The exception should contain the missing phone number.");
        assertTrue(thrown.getMessage().contains("0700000000"),
                "The exception message should include the missing phone number.");
    }

    @Test
    public void databaseFailurePhoneNumberShouldThrowDatabaseFailureException() {
        DatabaseFailureException thrown = assertThrows(DatabaseFailureException.class,
                () -> registry.findCustomer(CustomerRegistry.DATABASE_FAILURE_PHONE_NUMBER),
                "The hardcoded phone number should simulate a database failure.");

        assertEquals(CustomerRegistry.DATABASE_FAILURE_PHONE_NUMBER, thrown.getSearchValue(),
                "The exception should contain the search value that caused the failure.");
    }
}