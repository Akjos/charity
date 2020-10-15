package pl.coderslab.charity.institution;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.domain.model.Institution;
import pl.coderslab.charity.domain.repositories.InstitutionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InstitutionService {

    private final InstitutionRepository institutionRepository;

    public List<Institution> getInstitutionList() {
        log.debug("Get all institutions");
        return institutionRepository.findAll();
    }

    public Institution save(Institution institution) {
        return institutionRepository.save(institution);
    }

    public ResponseEntity getById(Long id) {
        return institutionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public Institution deleteById(Long id) {
        Institution institution = institutionRepository.getById(id);
        institutionRepository.delete(institution);
        return institution;
    }


    public Institution update(Institution institution) {
        return institutionRepository.save(institution);
    }
}
