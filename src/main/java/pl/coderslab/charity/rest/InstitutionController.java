package pl.coderslab.charity.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.domain.model.Institution;
import pl.coderslab.charity.institution.InstitutionDTO;
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
    public ResponseEntity create(@RequestBody InstitutionDTO institutionDTO, @RequestHeader HttpHeaders headers) {
        Long id = institutionService.save(institutionDTO);
        log.debug("institution get from JSON: {}", institutionDTO);
        headers.setLocation(URI.create("/api/institutions/" + id));
        log.debug("http headers: {}", headers);
        ResponseEntity responseEntity = new ResponseEntity(institutionDTO, headers, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable Long id) {
        ResponseEntity responseEntity = institutionService.getById(id);
        log.debug("ResponseEntity from db : {}", responseEntity);
        return responseEntity;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Institution deleteOne(@PathVariable Long id) {
        return institutionService.deleteById(id);
    }

    @PutMapping("/{id}")
    public Institution updateOne(@PathVariable Long id, @RequestBody InstitutionDTO institutionDTO) {
        return institutionService.update(id, institutionDTO);
    }
}
