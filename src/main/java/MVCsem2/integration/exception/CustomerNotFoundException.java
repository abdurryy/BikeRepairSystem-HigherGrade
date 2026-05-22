package MVCsem2.integration.exception;

/**
 * Thrown when a search for a customer phone number fails because the number 
 * does not exist in the customer registry. This represents alternative flow 5a.
 */
public class CustomerNotFoundException extends Exception {
    private final String phoneNumber;

    /**
     * Creates a new instance representing the missing phone number.
     *
     * @param phoneNumber The phone number that could not be found in the registry.
     */
    public CustomerNotFoundException(String phoneNumber) {
        super("No customer was found with the phone number: " + phoneNumber);
        this.phoneNumber = phoneNumber;
    }

    /**
     * Retrieves the phone number that caused the exception.
     *
     * @return The phone number that was not found.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
}
