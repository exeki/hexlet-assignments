package exercise.dto;


// BEGIN
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GuestCreateDTO {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @Size(min = 11, max = 13)
    @Pattern(regexp = "^\\+\\d+$", message = "String must start with a plus sign")
    private String phoneNumber;

    @Size(min = 4, max = 4)
    private String clubCard;

    @Future(message = "cardValidUntil must be in the future")
    private LocalDate cardValidUntil;

}
// END
