package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;
    @Spy
    private final CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    public void getAllCustomers() {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("Mike");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstName("Sofie");

        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        List<CustomerDTO> actual = customerService.getAllCustomers();

        assertEquals(2, actual.size());
    }

    @Test
    public void findById() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Mike");

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        CustomerDTO actual = customerService.findById(1L);

        assertEquals(Long.valueOf(1L), actual.getId());
        assertEquals("Mike", actual.getFirstName());
        assertEquals("/shop/customer/1", actual.getCustomerUrl());
    }
}