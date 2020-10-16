package pl.coderslab.charity.institution;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class InstitutionDTO {

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;
}
