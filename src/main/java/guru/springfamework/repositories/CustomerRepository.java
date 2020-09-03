package guru.springfamework.repositories;

import guru.springfamework.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Aleksandr Beryozkin
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
