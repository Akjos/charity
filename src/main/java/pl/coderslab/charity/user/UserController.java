package pl.coderslab.charity.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/register")
    public String prepareRegisterForm(Model model) {
        model.addAttribute("user", new UserRegisterDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerUserInDb(@ModelAttribute("user") @Valid UserRegisterDTO user, BindingResult bindingResult) {
        log.debug("UserDto data to save: {}", user);
        if(!user.getPassword().equals(user.getRepassword())) {
            bindingResult.rejectValue("repassword", "error.repassword", "Password don't match");
        }
        if(bindingResult.hasErrors()) {
            return "register";
        }
        userService.addUserToDb(user);
        return "redirect:/user/login";
    }

    @GetMapping("/login")
    public String prepareLoginForm() {
        return "login";
    }
}
