package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class VendorMapperTest {

    private static final VendorMapper MAPPER = VendorMapper.INSTANCE;
    public static final String FIRST_VENDOR = "first vendor";
    public static final String NAME = "name";

    @Test
    public void vendorToVendorDTO() {
        Vendor vendor = new Vendor(0L, FIRST_VENDOR);

        VendorDTO actual = MAPPER.vendorToVendorDTO(vendor);

        assertThat(actual)
            .isNotNull()
            .hasFieldOrPropertyWithValue(NAME, FIRST_VENDOR);
    }

    @Test
    public void vendorDTOToVendor() {
        VendorDTO vendorDTO = new VendorDTO(FIRST_VENDOR, null);

        Vendor actual = MAPPER.vendorDTOToVendor(vendorDTO);

        assertThat(actual)
            .isNotNull()
            .hasFieldOrPropertyWithValue(NAME, FIRST_VENDOR);
    }
}
