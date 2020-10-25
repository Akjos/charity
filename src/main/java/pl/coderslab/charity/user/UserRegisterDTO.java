package pl.coderslab.charity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    private String repassword;
}
