package co.com.credit.service.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "CARD_ACTIVATE_REG_TBL")
@ApiModel(value ="CardActivateRequest")
public class CardActivateRequest {

  @Id
  @Positive(message = "Id Must be positive")
  @ApiModelProperty(value = "Id of card", required = true)
  private Long id;



}
