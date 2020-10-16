package pl.coderslab.charity.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserRegisterDTO {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    private String repassword;
}
