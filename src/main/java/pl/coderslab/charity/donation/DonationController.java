package pl.coderslab.charity.donation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.category.CategoryService;
import pl.coderslab.charity.institution.InstitutionService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/donation")
@Slf4j
public class DonationController {
    private final DonationService donationService;
    private final CategoryService categoryService;
    private final InstitutionService institutionService;

    @GetMapping("/add")
    public String prepareFormToAddDonation(Model model) {
        model.addAttribute("categoryList", categoryService.getCategoryList());
        model.addAttribute("institutionList", institutionService.getInstitutionList());
        model.addAttribute("donation", new DonationAddFormDTO());
        return "/form";
    }

    @PostMapping("/add")
    public String addDonation(@ModelAttribute("donation") DonationAddFormDTO donationDTO) {
        log.warn("Donation add to database: {} ",donationDTO);
        return "redirect:/donation/add";
    }
}
