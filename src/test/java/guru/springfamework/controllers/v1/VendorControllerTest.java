package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.RestResponseEntityExceptionHandler;
import guru.springfamework.services.ResourceNotFoundException;
import guru.springfamework.services.VendorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VendorControllerTest extends AbstractRestControllerTest {

    @InjectMocks
    private VendorController controller;
    @Mock
    private VendorService service;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void testGetVendors() throws Exception {
        VendorDTO vendor1 = new VendorDTO();
        vendor1.setName("first vendor");

        VendorDTO vendor2 = new VendorDTO();
        vendor2.setName("second vendor");

        when(service.getAllVendors()).thenReturn(Arrays.asList(vendor1, vendor2));

        mockMvc.perform(get(VendorController.BASE_URL)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    public void testGetVendorById() throws Exception {
        VendorDTO vendor = new VendorDTO();
        vendor.setName("first vendor");

        when(service.getVendorById(anyLong())).thenReturn(vendor);

        mockMvc.perform(get(VendorController.BASE_URL + "1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.name", equalTo("first vendor")));
    }

    @Test
    public void testCreateVendor() throws Exception {
        VendorDTO vendor = new VendorDTO();
        vendor.setName("first vendor");

        when(service.createNewVendor(eq(vendor))).thenReturn(vendor);

        mockMvc.perform(post(VendorController.BASE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(vendor)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", equalTo("first vendor")));
    }

    @Test
    public void testDeleteVendor() throws Exception {

        mockMvc.perform(delete(VendorController.BASE_URL + "1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(service, times(1)).deleteVendorById(anyLong());
    }

    @Test
    public void testPatchVendor() throws Exception {
        VendorDTO vendor = new VendorDTO();
        vendor.setName("first vendor");

        when(service.patchVendor(anyLong(), eq(vendor))).thenReturn(vendor);

        mockMvc.perform(patch(VendorController.BASE_URL + "1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(vendor)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", equalTo("first vendor")));

        verify(service, times(1)).patchVendor(anyLong(), eq(vendor));
    }

    @Test
    public void testUpdateVendor() throws Exception {
        VendorDTO vendor = new VendorDTO();
        vendor.setName("first vendor");

        when(service.saveVendorByDTO(anyLong(), eq(vendor))).thenReturn(vendor);

        mockMvc.perform(put(VendorController.BASE_URL + "1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(vendor)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", equalTo("first vendor")));

        verify(service, times(1)).saveVendorByDTO(anyLong(), eq(vendor));
    }

    @Test
    public void testError() throws Exception {

        when(service.getVendorById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(VendorController.BASE_URL + "222")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }
}
