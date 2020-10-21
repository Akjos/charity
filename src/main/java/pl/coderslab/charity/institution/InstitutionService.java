package pl.coderslab.charity.institution;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.domain.model.Institution;
import pl.coderslab.charity.domain.repositories.InstitutionRepository;
import pl.coderslab.charity.rest.InvalidDataException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InstitutionService {

    private final InstitutionRepository institutionRepository;

    public List<Institution> getInstitutionList() {
        log.debug("Service: Get all institutions");
        return institutionRepository.findAll();
    }

    public Long save(InstitutionDTO institutionDTO) {
        Institution institution = new Institution();
        institution.setName(institutionDTO.getName());
        institution.setDescription(institutionDTO.getDescription());
        log.debug("Service: Institution to save in db: {}", institution);
        return institutionRepository.save(institution).getId();
    }

    public Optional<Institution> getById(Long id) {
        log.debug("Service: Institution get by id : {}", id);
        return institutionRepository.findById(id);
    }

    public void deleteById(Long id) {
        Optional<Institution> institution = institutionRepository.findById(id);
        if (!institution.isPresent()) {
            throw new InvalidDataException();
        }
        log.debug("Service: Institution to delete: {}", institution.get());
        institutionRepository.delete(institution.get());
    }


    public Institution update(Long id, InstitutionDTO institutionDTO) {
        Institution institution = institutionRepository.getById(id);
        log.debug("Service: Institution to delete: {}", institution);
        if (institution == null) {
            throw new InvalidDataException();
        }
        institution.setId(id);
        institution.setName(institutionDTO.getName());
        institution.setDescription(institutionDTO.getDescription());
        return institutionRepository.save(institution);
    }
}
