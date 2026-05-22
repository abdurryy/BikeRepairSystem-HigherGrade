package MVCsem2.model.dto;

import MVCsem2.model.entity.Customer;

/**
 * Carries the customer details needed by the view. This is an immutable data
 * carrier used to expose customer state across layers without leaking the
 * underlying entity.
 */
public class CustomerDetailsDTO {
    private final String name;
    private final String email;
    private final String phone;

    /**
     * Creates a new instance from a customer entity.
     *
     * @param customer The entity the data is extracted from.
     */
    public CustomerDetailsDTO(Customer customer) {
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.phone = customer.getPhoneNumber();
    }

    /**
     * @return The customer's name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The customer's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return The customer's phone number.
     */
    public String getPhone() {
        return phone;
    }
}
