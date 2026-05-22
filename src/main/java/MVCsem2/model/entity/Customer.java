package MVCsem2.model.entity;

/**
 * Defines a customer who owns a bicycle.
 */
public class Customer {
    private String name;
    private String email;
    private String phoneNumber;
    private Bike bike;

    /**
     * Constructs a customer with no details.
     */
    public Customer() {
        this("", "", "", new Bike());
    }

    /**
     * Constructs a customer with specified name, email, phone number, and bike.
     */
    public Customer(String name, String email, String phoneNumber, Bike bike) {
        this.name = valueOrEmpty(name);
        this.email = valueOrEmpty(email);
        this.phoneNumber = valueOrEmpty(phoneNumber);

        if (bike == null) {
            this.bike = new Bike();
        } else {
            this.bike = bike;
        }
    }

    /**
     * Constructs a customer with name, email, and bike, leaving phone number empty.
     */
    public Customer(String name, String email, Bike bike) {
        this(name, email, "", bike);
    }

    /**
     * Retrieves the bike associated with the customer.
     */
    public Bike getBike() {
        return bike;
    }

    /**
     * Retrieves the name of the customer.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the email address of the customer.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Retrieves the phone number of the customer.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    private String valueOrEmpty(String value) {
        if (value == null) {
            return "";
        }
        return value;
    }
}