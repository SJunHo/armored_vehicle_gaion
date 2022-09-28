package kr.gaion.armoredVehicle.auth;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
//@Table(name = "users")
@Getter
@Setter
public class User {
  @Id
  @Column(nullable = false, unique = true)
  private String id;

  @Column(nullable = false)
  private String password;

  @Column
//  private String role;
  private String usrth;
}
