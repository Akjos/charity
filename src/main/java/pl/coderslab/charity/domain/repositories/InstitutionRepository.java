package pl.coderslab.charity.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.charity.domain.model.Institution;

public interface InstitutionRepository extends JpaRepository<Institution, Long> {

    Institution getById(Long institutionId);
}
