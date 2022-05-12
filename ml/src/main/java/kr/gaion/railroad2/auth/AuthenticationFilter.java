package kr.gaion.railroad2.auth;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
  @NonNull private final UserRepository userRepository;
  @NonNull private final JwtIssuer jwtIssuer;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    final String requestTokenHeader = request.getHeader("Authorization");
    if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
      var decodedToken = jwtIssuer.verifyToken(requestTokenHeader.substring(7));
      var user = this.userRepository.findById(decodedToken.getClaim("username").asString());
      if (user.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Username or Password");
      }
      var tokenPayload = new AccessTokenPrincipal(user.get(), true);

      SecurityContextHolder.getContext().setAuthentication(tokenPayload);
    }

    filterChain.doFilter(request, response);
  }
}
