package pl.coderslab.charity.donation;

import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@ToString
public class DonationAddFormDTO {

    private List<Integer> categories;

    private Integer organization;

    private String zipCode;

    private String street;

    private String city;

    private Integer Quantity;

    private String pickUpComment;

    private String phone;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate pickUpDate;

    private LocalTime pickUpTime;

}
