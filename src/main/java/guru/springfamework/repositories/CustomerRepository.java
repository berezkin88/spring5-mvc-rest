package guru.springfamework.repositories;

import guru.springfamework.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Aleksandr Beryozkin
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
