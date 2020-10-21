package pl.coderslab.charity.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.institution.InstitutionDTO;
import pl.coderslab.charity.institution.InstitutionService;

import java.net.URI;

@RestController
@RequestMapping("/api/institutions")
@RequiredArgsConstructor
@Slf4j
public class InstitutionController {

    private final InstitutionService institutionService;

    @GetMapping
    public ResponseEntity getAll() {
        log.debug("Controller: Get all institution ");
        return ResponseEntity.ok(institutionService.getInstitutionList());
    }

    @PostMapping
    public ResponseEntity create(@RequestBody InstitutionDTO institutionDTO) {
        Long id = institutionService.save(institutionDTO);
        log.debug("Controller: Institution DTO get from JSON: {}", institutionDTO);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(URI.create("/api/institutions/" + id));
//        ResponseEntity responseEntity = new ResponseEntity(institutionDTO, headers, HttpStatus.OK);
        return ResponseEntity.ok().location(URI.create("/app/institutions/" + id)).body(institutionDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable Long id) {
        log.debug("Controller: Get institution by id: {}", id);
        return institutionService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOne(@PathVariable Long id) {
        log.debug("Controller: Delete institution by id: {}", id);
        institutionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateOne(@PathVariable Long id, @RequestBody InstitutionDTO institutionDTO) {
        log.debug("Controller: Update institution by id: {}, new date institution {}", id, institutionDTO);
        return ResponseEntity.ok().body(institutionService.update(id, institutionDTO));
    }
}
