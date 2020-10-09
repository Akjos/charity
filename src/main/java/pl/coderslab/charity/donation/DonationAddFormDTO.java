package pl.coderslab.charity.donation;

import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@ToString
public class DonationAddFormDTO {

    @NotNull
    private List<Long> categories;

    @NotNull
    private Long organization;

    private String zipCode;

    @NotNull
    private String street;

    @NotEmpty
    private String city;

    @NotNull
    private Integer Quantity;

    @Size(max = 2500)
    private String pickUpComment;

    private String phone;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate pickUpDate;

    private LocalTime pickUpTime;

}
