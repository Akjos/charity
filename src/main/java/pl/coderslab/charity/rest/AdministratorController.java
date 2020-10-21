package pl.coderslab.charity.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.user.UserRegisterDTO;
import pl.coderslab.charity.user.UserService;
import pl.coderslab.charity.user.UserViewDTO;

import javax.validation.Valid;
import java.net.URI;
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
        log.debug("User to save: {}", userRegisterDTO);
        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getRepassword())) {
            bindingResult.rejectValue("repassword", "error.repassword", "Password don't match");
        }
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList()), HttpStatus.BAD_REQUEST);
        }
        UserViewDTO userViewDTO = userService.saveAdmin(userRegisterDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/institutions/" + userViewDTO.getId()));
        return new ResponseEntity(userViewDTO, headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable Long id) {
        log.debug("Controller: Get user by id", id);
        return userService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOne(@PathVariable Long id) {
        log.debug("Delete user by id: {}", id);
        return new ResponseEntity(userService.deleteById(id), HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateOne(@PathVariable Long id, @RequestBody UserRegisterDTO userRegisterDTO) {
        log.debug("Update user: {}  by id: {}", userRegisterDTO, id);
        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getRepassword())) {
            return ResponseEntity.badRequest().body("Password don't match");
//            return new ResponseEntity("Password don't match", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().body(userService.update(id, userRegisterDTO));
//        return new ResponseEntity(userService.update(id, userRegisterDTO), HttpStatus.OK);
    }
}
