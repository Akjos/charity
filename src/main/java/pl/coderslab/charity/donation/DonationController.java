package pl.coderslab.charity.donation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.category.CategoryService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/donation")
public class DonationController {
    private final DonationService donationService;
    private final CategoryService categoryService;

    @GetMapping("/add")
    public String prepareFormToAddDonation(Model model) {
        model.addAttribute("categoryList", categoryService.getCategoryList());
        model.addAttribute("donation", new DonationAddFormDTO());
        return "/form";
    }
}
