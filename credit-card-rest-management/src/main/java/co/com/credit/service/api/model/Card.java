package co.com.credit.service.api.model;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Table(name = "CARD_REG_TBL")
public class Card {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "Number is required")
    @Size(max = 16, message = "Number has a maximum of 16 characters")
    private String cardNumber;

    @Transient
    private UUID corrId = UUID.randomUUID();

    @NotNull(message = "holderName is required")
    @Size(max = 20, message = "holderName has a maximum of 20 characters")
    private String holderName;

    @NotNull(message = "cardType is required")
    @Size(max = 6, message = "cardType has a maximum of 6 characters")
    private String cardType;

    @NotNull(message = "expiredDate is required")
    @Size(max = 8, message = "expiredDate has a maximum of 8 characters")
    private Date expiredDate;

    @NotNull(message = "csv is required")
    @Size(max = 3, message = "csv has a maximum of 3 characters")
    private String csv;

    @NotNull(message = "dailyLimit is required")
    @Size(max = 12, message = "dailyLimit has a maximum of 12 characters")
    private Double dailyLimit;

    @NotNull(message = "status is required")
    @Size(max = 16, message = "status has a maximum of 16 characters")
    private Boolean status;
}
