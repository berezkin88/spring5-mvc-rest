package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.v1.VendorController;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper = VendorMapper.INSTANCE;

    public VendorServiceImpl(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll()
            .stream()
            .map(vendor -> new VendorDTO(vendor.getName(), getVendorUrl(vendor.getId())))
            .collect(Collectors.toList());
    }

    private String getVendorUrl(Long id) {
        return VendorController.BASE_URL + id;
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorRepository.findById(id)
            .map(vendor -> new VendorDTO(vendor.getName(), getVendorUrl(vendor.getId())))
            .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        Vendor savedVendor = saveOrUpdate(vendorMapper.vendorDTOToVendor(vendorDTO));

        return new VendorDTO(savedVendor.getName(), getVendorUrl(savedVendor.getId()));
    }

    private Vendor saveOrUpdate(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    @Override // update ops
    public VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO) {
        Vendor updatedVendor = saveOrUpdate(new Vendor(id, vendorDTO.getName()));

        return new VendorDTO(updatedVendor.getName(), getVendorUrl(updatedVendor.getId()));
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id)
            .map(vendor -> {
                if (vendorDTO.getName() != null) {
                    vendor.setName(vendorDTO.getName());
                }

                Vendor patchedVendor = saveOrUpdate(vendor);

                return new VendorDTO(patchedVendor.getName(), getVendorUrl(patchedVendor.getId()));
            })
            .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }
}
