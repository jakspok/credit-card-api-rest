package co.com.credit.service.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "CARD_DAILY_LIMIT_REQUEST_REG_TBL")
@ApiModel(value ="CardDailyLimitRequest")
public class CardDailyLimitRequest {

  @Id
  @Positive(message = "Id Must be positive")
  @ApiModelProperty(value = "id  of card", required = true)
  private Long id;

  @NotNull(message = "Daily Not Allow empty")
  @ApiModelProperty(value = "Daily limit of card", required = true, example = "1000.00")
  private Double balance;



}
