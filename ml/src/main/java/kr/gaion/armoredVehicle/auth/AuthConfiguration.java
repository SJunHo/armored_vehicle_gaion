//package kr.gaion.armoredVehicle.auth;
//
//import lombok.NonNull;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.security.web.authentication.HttpStatusEntryPoint;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class AuthConfiguration extends WebSecurityConfigurerAdapter {
//  @NonNull private final AuthenticationFilter authenticationFilter;
//
//  @Override
//  public void configure(HttpSecurity http) throws Exception {
//    http.sessionManagement()
//        .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//        .exceptionHandling().and()
//        .authorizeRequests().antMatchers("/api/*").authenticated()
//        .anyRequest().permitAll().and()
//        .csrf().disable()
//        .formLogin().disable()
//        .httpBasic().disable()
//        .logout().disable();
//    http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
//    http.cors();
//  }
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//  @Bean
//  AuthenticationEntryPoint forbiddenEntryPoint() {
//    return new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED);
//  }
//}