package kr.gaion.armoredVehicle.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtIssuer {
  @NonNull private final JwtConfiguration authConfiguration;

  public DecodedJWT verifyToken(String token) throws JWTDecodeException, TokenExpiredException {
    var verifier = JWT.require(Algorithm.HMAC512(authConfiguration.getSecret()))
        .withIssuer(authConfiguration.getIssuer())
        .acceptLeeway(1)
        .build();
    return verifier.verify(token);
  }

  public String signToken(User user) {
    return JWT.create()
        .withIssuer(authConfiguration.getIssuer())
        .withExpiresAt(
            Date.from(
                LocalDateTime.now().plusHours(authConfiguration.getExpiresAfter()).atZone(ZoneId.systemDefault()).toInstant()
            ))
        .withIssuer(authConfiguration.getIssuer())
            //.withClaim("username", user.getUsername())
            // username -> id
        .withClaim("id", user.getUserid())
//        .withClaim("role", user.getRole())
        .withClaim("role", user.getUsrth())
        .sign(Algorithm.HMAC512(authConfiguration.getSecret()));
  }
}
