package pl.coderslab.charity.institution;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.domain.model.Institution;
import pl.coderslab.charity.domain.repositories.InstitutionRepository;
import pl.coderslab.charity.rest.InvalidDataException;

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

    public Long save(InstitutionDTO institutionDTO) {
        Institution institution = new Institution();
        institution.setName(institutionDTO.getName());
        institution.setDescription(institutionDTO.getDescription());
        return institutionRepository.save(institution).getId();
    }

    public ResponseEntity getById(Long id) {
        return institutionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public Institution deleteById(Long id) {
        Institution institution = institutionRepository.getById(id);
        log.debug("Institution to delete: {}", institution);
        if (institution == null) {
            throw new InvalidDataException();
        }
        institutionRepository.delete(institution);
        return institution;
    }


    public Institution update(Long id, InstitutionDTO institutionDTO) {
        Institution institution = institutionRepository.getById(id);
        log.debug("Institution to delete: {}", institution);
        if (institution == null) {
            throw new InvalidDataException();
        }
        institution.setId(id);
        institution.setName(institutionDTO.getName());
        institution.setDescription(institutionDTO.getDescription());
        return institutionRepository.save(institution);
    }
}
