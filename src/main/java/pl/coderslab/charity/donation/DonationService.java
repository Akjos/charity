package pl.coderslab.charity.donation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.domain.model.Category;
import pl.coderslab.charity.domain.model.Donation;
import pl.coderslab.charity.domain.repositories.CategoryRepository;
import pl.coderslab.charity.domain.repositories.DonationRepository;
import pl.coderslab.charity.domain.repositories.InstitutionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DonationService {

    private final DonationRepository donationRepository;
    private final InstitutionRepository institutionRepository;
    private final CategoryRepository categoryRepository;

    public Integer getSumQuantityDonation() {
        return donationRepository.getSumQuantityDonation();
    }

    public Integer getDonationCounter() {
        return donationRepository.countAll();
    }

    public void saveDonation(DonationAddFormDTO donationDTO) {
        Donation donationToSave = new Donation();
        donationToSave.setQuantity(donationDTO.getQuantity());
        donationToSave.setStreet(donationDTO.getStreet());
        donationToSave.setCity(donationDTO.getCity());
        donationToSave.setZipCode(donationDTO.getZipCode());
        donationToSave.setPickUpDate(donationDTO.getPickUpDate());
        donationToSave.setPickUpTime(donationDTO.getPickUpTime());
        donationToSave.setPickUpComment(donationDTO.getPickUpComment());
        donationToSave.setInstitution(institutionRepository.getById(donationDTO.getOrganization()));
        donationToSave.setCategories(getCategoryList(donationDTO.getCategories()));
        log.debug("Donation to save in database: {}", donationToSave);
        donationRepository.save(donationToSave);
    }

    private List<Category> getCategoryList(List<Long> categories) {
        return categories.stream()
                .map(e-> categoryRepository.getById(e))
                .collect(Collectors.toList());
    }
}
