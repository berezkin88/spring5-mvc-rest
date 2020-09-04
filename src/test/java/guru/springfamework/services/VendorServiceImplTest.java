package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.v1.VendorController;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VendorServiceImplTest {

    public static final String FIRST_VENDOR = "first vendor";
    public static final String NAME_FIELD = "name";
    public static final String VENDOR_URL_FIELD = "vendorUrl";
    public static final String VENDOR_URL_VALUE = VendorController.BASE_URL + "1";

    @InjectMocks
    private VendorServiceImpl service;

    @Mock
    private VendorRepository repository;
    @Spy
    private final VendorMapper mapper = VendorMapper.INSTANCE;

    @Test
    public void getAllVendors() {
        Vendor vendor1 = new Vendor();
        vendor1.setId(1L);
        vendor1.setName(FIRST_VENDOR);

        Vendor vendor2 = new Vendor();
        vendor2.setId(2L);
        vendor2.setName("second vendor");

        when(repository.findAll()).thenReturn(Arrays.asList(vendor1, vendor2));

        List<VendorDTO> actual = service.getAllVendors();

        assertThat(actual)
            .isNotEmpty()
            .hasSize(2)
            .extracting(VENDOR_URL_FIELD)
            .containsOnlyOnce(VENDOR_URL_VALUE);
    }

    @Test
    public void getVendorById() {
        Vendor vendor = new Vendor();
        vendor.setId(1L);
        vendor.setName(FIRST_VENDOR);

        when(repository.findById(anyLong())).thenReturn(Optional.of(vendor));

        VendorDTO actual = service.getVendorById(1L);

        assertThat(actual)
            .isNotNull()
            .hasFieldOrPropertyWithValue(NAME_FIELD, FIRST_VENDOR)
            .hasFieldOrPropertyWithValue(VENDOR_URL_FIELD, VENDOR_URL_VALUE);
    }

    @Test
    public void createNewVendor() {
        Vendor vendor = new Vendor();
        vendor.setId(1L);
        vendor.setName(FIRST_VENDOR);

        when(repository.save(any(Vendor.class))).thenReturn(vendor);

        VendorDTO actual = service.createNewVendor(new VendorDTO());

        assertThat(actual)
            .isNotNull()
            .hasFieldOrPropertyWithValue(NAME_FIELD, FIRST_VENDOR)
            .hasFieldOrPropertyWithValue(VENDOR_URL_FIELD, VENDOR_URL_VALUE);
    }

    @Test
    public void saveVendorByDTO() {
        Vendor vendor = new Vendor();
        vendor.setId(1L);
        vendor.setName(FIRST_VENDOR);

        when(repository.save(eq(vendor))).thenReturn(vendor);

        VendorDTO actual = service.saveVendorByDTO(1L, mapper.vendorToVendorDTO(vendor));

        assertThat(actual)
            .isNotNull()
            .hasFieldOrPropertyWithValue(NAME_FIELD, FIRST_VENDOR)
            .hasFieldOrPropertyWithValue(VENDOR_URL_FIELD, VENDOR_URL_VALUE);
    }

    @Test
    public void patchVendor() {
        Vendor vendorWithId = new Vendor();
        vendorWithId.setId(1L);

        Vendor vendor = new Vendor();
        vendor.setId(1L);
        vendor.setName(FIRST_VENDOR);

        when(repository.findById(anyLong())).thenReturn(Optional.of(vendorWithId));
        when(repository.save(eq(vendor))).thenReturn(vendor);

        VendorDTO actual = service.patchVendor(1L, mapper.vendorToVendorDTO(vendor));

        assertThat(actual)
            .isNotNull()
            .hasFieldOrPropertyWithValue(NAME_FIELD, FIRST_VENDOR)
            .hasFieldOrPropertyWithValue(VENDOR_URL_FIELD, VENDOR_URL_VALUE);
    }

    @Test
    public void deleteVendorById() {
        service.deleteVendorById(1L);

        verify(repository, times(1)).deleteById(eq(1L));
    }
}
