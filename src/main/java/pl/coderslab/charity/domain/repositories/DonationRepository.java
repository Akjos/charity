package pl.coderslab.charity.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.charity.domain.model.Donation;

public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Query("SELECT SUM(d.quantity) FROM Donation d")
    Integer getSumQuantityDonation();

    @Query("SELECT COUNT(d) FROM Donation d")
    Integer countAll();
}
