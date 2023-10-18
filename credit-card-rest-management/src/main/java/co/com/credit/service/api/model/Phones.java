package co.com.credit.service.api.model;

import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@ApiModel(value ="Phones")
@Entity
@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Table(name = "PHONES_REG_TBL_PHO")
public class Phones implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private int id;

  @NotNull(message = "number  is required")
  @Size(max = 10, message = "Number has a maximum of 10 characters")
  private String number;

  @NotNull(message = "cityCode is required")
  @Size(max = 10, message = "cityCode has a maximum of 10 characters")
  private String cityCode;

  @NotNull(message = "countryCode  is required")
  @Size(max = 10, message = "countryCode has a maximum of 10 characters")
  private String countryCode;
}
