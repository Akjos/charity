package pl.coderslab.charity.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.domain.model.Institution;
import pl.coderslab.charity.institution.InstitutionService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/institutions")
@RequiredArgsConstructor
@Slf4j
public class InstitutionController {

    private final InstitutionService institutionService;

    @GetMapping
    public List<Institution> getAll() {
        return institutionService.getInstitutionList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Institution create(@RequestBody Institution institution, @RequestHeader HttpHeaders headers) {
        institution = institutionService.save(institution);
        log.debug("institution get from JSON: {}", institution);
        headers.setLocation(URI.create("/api/institutions/" + institution.getId()));
        log.debug("http headers: {}", headers);
        return institution;
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable Long id) {
        return institutionService.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Institution deleteOne(@PathVariable Long id) {
        return institutionService.deleteById(id);
    }

    @PutMapping("/{id}")
    public Institution updateOne(@RequestBody Institution institution) {
        return institutionService.update(institution);
    }
}
