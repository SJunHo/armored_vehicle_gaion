package kr.gaion.armoredVehicle.auth;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.security.sasl.AuthenticationException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthorizationService {
  @NonNull private final UserRepository userRepository;
  @NonNull private final JwtIssuer jwtIssuer;
  private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

  public AuthDTO.AuthResponseDTO loginUser(AuthDTO.AuthRequestDTO authRequestDTO) throws AuthenticationException {
    Optional<User> user = userRepository.findById(authRequestDTO.username);
    if (user.isEmpty() || !bCryptPasswordEncoder.matches(authRequestDTO.password, user.get().getPassword())) {
      throw new AuthenticationException("Invalid username and password");
    }

    String token = jwtIssuer.signToken(user.get());
    var authResponse = new AuthDTO.AuthResponseDTO();
    authResponse.token = token;
    return authResponse;
  }
}
