package kr.gaion.armoredVehicle.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.gaion.armoredVehicle.web.security.jwt.JwtUtils;
import kr.gaion.armoredVehicle.web.utils.LogInterceptor;
import lombok.RequiredArgsConstructor;


@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer{

//	@Autowired
//	private final JwtUtils jwtUtils;
	final LogInterceptor loginterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginterceptor)
        .order(1)
        .addPathPatterns("/**")
        .excludePathPatterns("/css/**", "/*.ico", "/error");
	}
	
}
