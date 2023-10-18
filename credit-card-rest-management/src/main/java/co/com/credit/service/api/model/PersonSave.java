package co.com.credit.service.api.model;

import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@ApiModel(value ="PersonSave")
@Entity
@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Table(name = "PERSON_SAVE_REG_TBL")
public class PersonSave implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private String id;
  private String created;
  private String modified;
  private String last_login;
  private String isactive;
}
