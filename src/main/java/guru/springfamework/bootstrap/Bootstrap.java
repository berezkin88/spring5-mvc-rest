package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author Aleksandr Beryozkin
 */
@Component
public class Bootstrap implements CommandLineRunner{

    private final CategoryRepository categoryRespository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRespository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRespository = categoryRespository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();

        loadCustomers();

        loadVendors();
    }

    private void loadCustomers() {
        Customer mike = new Customer();
        mike.setFirstName("Mike");
        mike.setLastName("Werbniy");

        Customer lora = new Customer();
        lora.setFirstName("Lora");
        lora.setLastName("Eizhek");

        Customer robert = new Customer();
        lora.setFirstName("Robert");
        lora.setLastName("Potter");

        customerRepository.save(mike);
        customerRepository.save(lora);
        customerRepository.save(robert);

        System.out.println("Customers Loaded = " + customerRepository.count());
    }

    private void loadCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRespository.save(fruits);
        categoryRespository.save(dried);
        categoryRespository.save(fresh);
        categoryRespository.save(exotic);
        categoryRespository.save(nuts);

        System.out.println("Categories Loaded = " + categoryRespository.count());
    }

    private void loadVendors() {
        Vendor fruitsCompany = new Vendor();
        fruitsCompany.setName("Western Tasty Fruits Ltd.");

        Vendor nutsCompany = new Vendor();
        nutsCompany.setName("Nuts for Nuts Company");

        vendorRepository.save(fruitsCompany);
        vendorRepository.save(nutsCompany);

        System.out.println("Vendors Loaded = " + vendorRepository.count());
    }
}
