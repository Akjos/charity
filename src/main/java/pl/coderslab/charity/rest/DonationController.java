package pl.coderslab.charity.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.domain.model.Donation;
import pl.coderslab.charity.domain.repositories.DonationRepository;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/donations")
@RequiredArgsConstructor
public class DonationController {

    private final DonationRepository donationRepository;

    @GetMapping
    public List<Donation> getAll() {
        return donationRepository.findAll();
    }

    // REST: przy utworzeniu nowego zasobu
    // zwracamy kod odpowiedzi 204 CREATED oraz nagłówek Location
    // z adresem nowo utworzonego zasobu
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Donation create(@Valid @RequestBody Donation donation, HttpHeaders headers) {
        donationRepository.save(donation);
        headers.setLocation(
                URI.create("/api/donations/" + donation.getId()));
        return donation;
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable Long id) {
        return donationRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Donation deleteOne(@PathVariable Long id) {
        Donation donation = donationRepository.getOne(id);
        donationRepository.delete(donation);
        return donation;
    }

    @PutMapping("/{id}")
    public Donation updateOne(@RequestBody Donation donation) {
        donationRepository.save(donation);
        return donation;
    }

}
