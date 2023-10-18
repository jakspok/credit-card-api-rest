package co.com.credit.service.api.model;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.data.domain.Auditable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;


@ApiModel
@Entity
@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Table(name = "CARD_REG_TBL")
public class Card implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Size(max = 16, message = "Number has a maximum of 16 characters")
    private Long cardNumber;

    @NotNull(message = "holderName is required")
    @Size(max = 20, message = "holderName has a maximum of 20 characters")
    private String holderName;

    @Size(max = 6, message = "ProductType has a maximum of 6 characters")
    @Enumerated(EnumType.ORDINAL)
    private TypeProduct productId;

    @NotNull(message = "expiredDate is required")
    @Size(max = 8, message = "expiredDate has a maximum of 8 characters")
    private Date expiredDate;

    @NotNull(message = "csv is required")
    @Size(max = 3, message = "csv has a maximum of 3 characters")
    private String csv;

    @Size(max = 12, message = "dailyLimit has a maximum of 12 characters")
    private Double dailyLimit;

    @Size(max = 10, message = "status has a maximum of 16 characters")
    @Enumerated(EnumType.ORDINAL)
    private Status status;
}
