package co.com.credit.service.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@ApiModel(value ="CardDailyLimitRequest")
@Entity
@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Table(name = "CARD_DAILY_LIMIT_REQUEST_REG_TBL")
public class CardDailyLimitRequest implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Positive(message = "Id Must be positive")
  @ApiModelProperty(value = "id  of card", required = true)
  private Long id;

  @NotNull(message = "Daily Not Allow empty")
  @ApiModelProperty(value = "Daily limit of card", required = true, example = "1000.00")
  private Double balance;



}
