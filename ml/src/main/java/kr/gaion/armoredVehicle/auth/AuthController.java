package kr.gaion.armoredVehicle.auth;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.security.sasl.AuthenticationException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController("/kr/gaion/armoredVehicle/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class AuthController {

  @NonNull private final AuthorizationService authorizationService;

  @PostMapping("/auth/login")
  public ResponseEntity userLogin(@RequestBody AuthDTO.AuthRequestDTO authRequestDTO) throws InvalidKeySpecException, NoSuchAlgorithmException, AuthenticationException {
    try {
      return ResponseEntity.status(200).body(authorizationService.loginUser(authRequestDTO));
    } catch (AuthenticationException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Username and Password");
    }
  }
}
