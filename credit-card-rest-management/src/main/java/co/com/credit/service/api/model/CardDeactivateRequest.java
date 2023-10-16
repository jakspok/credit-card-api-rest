package co.com.credit.service.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "CARD_DEACTIVATE_REG_TBL")
@ApiModel(value ="CardDeactivateRequest")
public class CardDeactivateRequest {

  @Id
  @Positive(message = "Id Must be positive")
  @ApiModelProperty(value = "Id of card", required = true)
  private Long id;



}
