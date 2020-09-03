package guru.springfamework.services;

import guru.springfamework.api.v1.model.CustomerDTO;

import java.util.List;

/**
 * @author Aleksandr Beryozkin
 */

public interface CustomerService {
    List<CustomerDTO> getAllCustomers();

    CustomerDTO findById(Long id);

    CustomerDTO createNewCustomer(CustomerDTO customerDTO);
}
