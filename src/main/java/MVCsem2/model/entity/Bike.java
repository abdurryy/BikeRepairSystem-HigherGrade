package MVCsem2.model.entity;

/**
 * Represents a customer's bike.
 */
public class Bike {
    private String brand;
    private String model;
    private String serialNumber;

    /**
     * Constructs an empty bike instance.
     */
    public Bike() {
        this("", "", "");
    }

    /**
     * Constructs a bike with specified brand, model, and serial number.
     */
    public Bike(String brand, String model, String serialNumber) {
        this.brand = valueOrEmpty(brand);
        this.model = valueOrEmpty(model);
        this.serialNumber = valueOrEmpty(serialNumber);
    }

    /**
     * Retrieves the brand of the bike.
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Retrieves the model of the bike.
     */
    public String getModel() {
        return model;
    }

    /**
     * Retrieves the serial number of the bike.
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    private String valueOrEmpty(String value) {
        if (value == null) {
            return "";
        }
        return value;
    }
}