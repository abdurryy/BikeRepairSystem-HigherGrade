package MVCsem2.model.dto;

import MVCsem2.model.entity.Bike;
import MVCsem2.model.entity.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerDetailsDTOTest {

    @Test
    public void registeredCustomerDetailsShouldBeCopiedToDTO() {
        
        String customerName = "Cristiano Ronaldo";
        String customerEmail = "CR7@mail.com";
        String customerPhone = "0737654321";

        Bike registeredBike = new Bike("Scotter", " 1", "SET401");
        Customer registeredCustomer = new Customer(customerName, customerEmail, customerPhone, registeredBike);

        CustomerDetailsDTO customerDetails = new CustomerDetailsDTO(registeredCustomer);

        assertEquals(customerName, customerDetails.getName(),
                "The DTO should contain Cristiano Ronaldo's name.");
        assertEquals(customerEmail, customerDetails.getEmail(),
                "The DTO should contain Cristiano Ronaldo's email.");
        assertEquals(customerPhone, customerDetails.getPhone(),
                "The DTO should contain Cristiano Ronaldo's phone number.");
    }
}