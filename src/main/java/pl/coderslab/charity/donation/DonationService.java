package pl.coderslab.charity.donation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.domain.repositories.DonationRepository;

@Service
@RequiredArgsConstructor
public class DonationService {

    private final DonationRepository donationRepository;

    public Integer getSumQuantityDonation() {
        return donationRepository.getSumQuantityDonation();
    }

    public Integer getDonationCounter() {
        return donationRepository.countAll();
    }
}
