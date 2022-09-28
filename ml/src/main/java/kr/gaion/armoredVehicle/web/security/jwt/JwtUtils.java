package kr.gaion.armoredVehicle.web.security.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import kr.gaion.armoredVehicle.auth.JwtConfiguration;
import kr.gaion.armoredVehicle.auth.JwtIssuer;
import kr.gaion.armoredVehicle.web.security.jwt.model.User;
import kr.gaion.armoredVehicle.web.security.jwt.request.LoginRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import kr.gaion.armoredVehicle.web.security.service.UserDetailsImpl;

@Component
@RequiredArgsConstructor
public class JwtUtils {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.jwtExpirationMs}")
  private int jwtExpirationMs;


  @NonNull private final JwtIssuer jwtIssuer;
  @NonNull private final JwtConfiguration authConfiguration;
//  public String generateJwtToken(Authentication authentication) {
//
//    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
//
//    //jwt토큰을 만드는데 사용하는 파라미터들 userId가 들어감
////    return Jwts.builder()
////        .setSubject((userPrincipal.getId()))
////        .setIssuedAt(new Date())
////        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
////        .signWith(SignatureAlgorithm.HS512, jwtSecret)
////        .compact();
//    return JWT.create()
//            .withIssuer(authConfiguration.getIssuer())
//            .withExpiresAt(
//                    Date.from(
//                            LocalDateTime.now().plusHours(authConfiguration.getExpiresAfter()).atZone(ZoneId.systemDefault()).toInstant()
//                    ))
//            .withIssuer(authConfiguration.getIssuer())
//            .withClaim("id", user.getId())
////        .withClaim("role", user.getRole())
//            .withClaim("role", user.getUsrth())
//            .sign(Algorithm.HMAC512(authConfiguration.getSecret()));
//  }
  public String generateJwtToken(User user) {
    return JWT.create()
            .withIssuer(authConfiguration.getIssuer())
            .withExpiresAt(
                    Date.from(
                            LocalDateTime.now().plusHours(authConfiguration.getExpiresAfter()).atZone(ZoneId.systemDefault()).toInstant()
                    ))
            .withIssuer(authConfiguration.getIssuer())
            .withClaim("id", user.getId())
//        .withClaim("role", user.getRole())
            .withClaim("role", String.valueOf(user.getUsrth()))
            .sign(Algorithm.HMAC512(authConfiguration.getSecret()));
  }
  public String getUserNameFromJwtToken(String token) {
    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      logger.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }
}
