package kr.gaion.armoredVehicle.web.security.service;

import java.util.ArrayList;
import java.util.Collection;

import java.util.List;
import java.util.Objects;

import kr.gaion.armoredVehicle.web.security.jwt.model.Usercd;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

  private String userid;

  private String name;

  @JsonIgnore
  private String pwd;

  private Collection<? extends GrantedAuthority> authorities;

  public UserDetailsImpl(String userid, String name, String pwd,
                         Collection<? extends GrantedAuthority> authorities) {
    this.userid = userid;
    this.name = name;
    this.pwd = pwd;
    this.authorities = authorities;
  }

  public static UserDetailsImpl build(Usercd usercd) {
    /*
     * List<GrantedAuthority> authorities = user.getRoles().stream() .map(role ->
     * new SimpleGrantedAuthority(role.getName().name()))
     * .collect(Collectors.toList());
     */
    List<GrantedAuthority> authorities = new ArrayList<>();
    if(usercd.getUsrth() == 'N') {
      authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    }else if(usercd.getUsrth() == 'A') {
      authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }else if(usercd.getUsrth() == 'M') {
      authorities.add(new SimpleGrantedAuthority("ROLE_MODERATOR"));
    }

    return new UserDetailsImpl(
            usercd.getUserid(),
            usercd.getName(),
            usercd.getPwd(),
            authorities);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public String getUserId() {
    return userid;
  }

  @Override
  public String getPassword() {
    return pwd;
  }

  @Override
  public String getUsername() {
    return name;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(userid, user.userid);
  }
}
