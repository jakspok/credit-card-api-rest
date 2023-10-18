package co.com.credit.service.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

@ApiModel(value ="CardActivateRequest")
@Entity
@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Table(name = "CARD_ACTIVATE_REG_TBL")
public class CardActivateRequest implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Positive(message = "Id Must be positive")
  @ApiModelProperty(value = "Id of card", required = true)
  private Long id;

  @NotNull(message = "status is required")
  @Size(max = 16, message = "status has a maximum of 16 characters")
  private Boolean status;



}
