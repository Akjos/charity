package pl.coderslab.charity.user;

import lombok.Data;

@Data
public class UserRegisterDTO {

    private String username;

    private String password;

    private String repassword;
}
