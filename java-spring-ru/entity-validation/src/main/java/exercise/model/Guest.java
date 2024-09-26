package exercise.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.validation.constraints.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;




@Entity
@Table(name = "guests")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Guest {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    // BEGIN

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

    // END

    @CreatedDate
    private LocalDate createdAt;
}
