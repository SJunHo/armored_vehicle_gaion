package kr.gaion.railroad2.auth;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
@Getter
@Setter
public class User {
  @Id
  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column
  private String email;

  @Column(nullable = false)
  private String firstName;

  @Column()
  private String lastName;

  @Column()
  private String address;

  @Column
  private String zip;

  @Column
  private String phone;

  @Column
  private String role;
}
