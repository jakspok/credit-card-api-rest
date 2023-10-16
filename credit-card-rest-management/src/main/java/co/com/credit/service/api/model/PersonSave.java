package co.com.credit.service.api.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "PERSON_SAVE_REG_TBL")
public class PersonSave implements Serializable {

  @Id
  private String id;
  private String created;
  private String modified;
  private String last_login;
  private String isactive;
}
