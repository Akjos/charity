package pl.coderslab.charity.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.user.UserRegisterDTO;
import pl.coderslab.charity.user.UserService;
import pl.coderslab.charity.user.UserViewDTO;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user/administrators")
@RequiredArgsConstructor
@Slf4j
public class AdministratorController {

    private final UserService userService;

    @GetMapping
    public List<UserViewDTO> getAll() {
        return userService.getAllAdmin();
    }

    @PostMapping
    public ResponseEntity created(@Valid @RequestBody UserRegisterDTO userRegisterDTO, BindingResult bindingResult) {
        if(!userRegisterDTO.getPassword().equals(userRegisterDTO.getRepassword())) {
            bindingResult.rejectValue("repassword", "error.repassword", "Password don't match");
        }
        if(bindingResult.hasErrors()) {
//            Czy dodawać bindingResult errors?
            return new ResponseEntity(bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList()), HttpStatus.BAD_REQUEST);
//            return ResponseEntity.badRequest().build();
        }
//        Czy chciałbym tu zwracać URL w headers?
        return new ResponseEntity(userService.saveAdmin(userRegisterDTO), HttpStatus.CREATED);
    }
}