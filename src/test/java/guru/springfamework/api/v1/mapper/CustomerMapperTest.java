package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerMapperTest {

    public static final Long ID = 1L;
    public static final String NAME = "Mike";
    private final CustomerMapper mapper = CustomerMapper.INSTANCE;

    @Test
    public void customerToCustomerDTO() {
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(NAME);

        CustomerDTO actual = mapper.customerToCustomerDTO(customer);

        assertEquals(ID, actual.getId());
        assertEquals(NAME, actual.getFirstName());
        assertNull(actual.getCustomerUrl());
    }
}