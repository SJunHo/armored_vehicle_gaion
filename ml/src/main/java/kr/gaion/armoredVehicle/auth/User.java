package kr.gaion.armoredVehicle.auth;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USERCD")
@Getter
@Setter
public class User {
  @Id
  @Column(nullable = false, unique = true)
  private String userid;

  @Column(nullable = false)
  private String pwd;

  @Column
//  private String role;
  private String usrth;
}
