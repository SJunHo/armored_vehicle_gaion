package kr.gaion.armoredVehicle.auth;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.testng.collections.Lists;

import java.util.Collection;

@RequiredArgsConstructor
@Getter
public class AccessTokenPrincipal implements Authentication {
  @NonNull private final User user;
  @NonNull private Boolean isAuthenticated;

  @Override
  public String getName() {
    return "AccessTokenPrincipal";
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // usrth에 따른 권한 설정
    if("M".equals(user.getUsrth())){
      return Lists.newArrayList(new SimpleGrantedAuthority("ROLE_MODERATOR"));
    }else if("A".equals(user.getUsrth())){
      return Lists.newArrayList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }else if("N".equals(user.getUsrth())) {
      return Lists.newArrayList(new SimpleGrantedAuthority("ROLE_USER"));
    }else {
      return null;
    }
  }

  @Override
  public Object getCredentials() {
    return user;
  }

  @Override
  public Object getDetails() {
    return user;
  }

  @Override
  public Object getPrincipal() {
    return this;
  }

  @Override
  public boolean isAuthenticated() {
    return isAuthenticated;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    if (isAuthenticated) {
      throw new IllegalArgumentException("Can set this as trusted credential");
    }
    this.isAuthenticated = false;
  }
}
