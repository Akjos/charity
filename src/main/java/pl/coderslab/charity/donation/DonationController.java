package pl.coderslab.charity.donation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.category.CategoryService;
import pl.coderslab.charity.institution.InstitutionService;

import javax.validation.Valid;

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
    public String addDonation(@ModelAttribute("donation") @Valid DonationAddFormDTO donationDTO, BindingResult bindingResult, Model model) {
        log.debug("Donation pass to controller: {} ",donationDTO);
        if(bindingResult.hasErrors()) {
            model.addAttribute("categoryList", categoryService.getCategoryList());
            model.addAttribute("institutionList", institutionService.getInstitutionList());
            return "/form";
        }
        donationService.saveDonation(donationDTO);
        return "redirect:/donation/add";
    }
}
