package co.com.credit.service.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PHONES_REG_TBL_PHO")
public class Phones {

  @Id @GeneratedValue private int id;

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
