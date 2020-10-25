package pl.coderslab.charity.institution;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstitutionDTO {

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    public static InstitutionDTOBuilder builder() {
        return new InstitutionDTOBuilder();
    }

    public static class InstitutionDTOBuilder {
        private @NotEmpty String name;
        private @NotEmpty String description;

        InstitutionDTOBuilder() {
        }

        public InstitutionDTOBuilder name(@NotEmpty String name) {
            this.name = name;
            return this;
        }

        public InstitutionDTOBuilder description(@NotEmpty String description) {
            this.description = description;
            return this;
        }

        public InstitutionDTO build() {
            return new InstitutionDTO(name, description);
        }

        public String toString() {
            return "InstitutionDTO.InstitutionDTOBuilder(name=" + this.name + ", description=" + this.description + ")";
        }
    }
}
