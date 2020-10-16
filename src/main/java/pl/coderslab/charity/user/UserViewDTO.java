package pl.coderslab.charity.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class UserViewDTO {

    private Long id;

    @NotEmpty
    private String username;

    @NotEmpty
    private String role;

    public UserViewDTO(Long id, @NotEmpty String username, @NotEmpty String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }
}
