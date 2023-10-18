package co.com.credit.service.api.model;
import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Date;


@ApiModel
@Entity
@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Table(name = "TRANSACTION_REG_TBL")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Size(max = 16, message = "Number has a maximum of 16 characters")
    private Long cardId;

    @Size(max = 100, message = "price has a maximum of 100")
    private Double price;


}
