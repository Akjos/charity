package pl.coderslab.charity.donation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/donation")
public class DonationController {
    private final DonationService donationService;

    @GetMapping("/add")
    public String prepareFormToAddDonation(Model model) {
        return "/form";
    }
}
