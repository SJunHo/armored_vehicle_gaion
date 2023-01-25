package kr.gaion.armoredVehicle.web.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.gaion.armoredVehicle.auth.JwtIssuer;
import lombok.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.gaion.armoredVehicle.web.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LogInterceptor implements HandlerInterceptor {

	private final JwtUtils jwtUtils;

	@NonNull
	private final JwtIssuer jwtIssuer;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		final String requestTokenHeader = request.getHeader("Authorization");
	    String requestURI = request.getRequestURI();
	    String message = null;
		String reqBody = (String) request.getAttribute("requestBody");
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
		var decodedToken = jwtIssuer.verifyToken(requestTokenHeader.substring(7).replaceAll("\\\"", ""));
	    	  //jwtToken에서 userId를 가져온다
	      String userId = decodedToken.getClaim("id").asString();
	      if(reqBody != null) {
	    	  message = userId + " " + requestURI +" " + reqBody;
	      }else {	    	  
	    	  message = userId + " " + requestURI;
	      }
	      this.saveLog(message);
	    }
    
	    return true;
	}
	
	private String parseJwt(HttpServletRequest request) {
	    String headerAuth = request.getHeader("Authorization");

	    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
	      return headerAuth.substring(7, headerAuth.length());
	    }

	    return null;
	 }
	
	private void saveLog(String message) {
		try {
			Date today = new Date();
			SimpleDateFormat sd = new SimpleDateFormat("YYYY-MM-dd");

			String path = System.getProperty("user.dir") + File.separator + 
					"log" + File.separator + 
					sd.format(today) + ".txt";
			File file = new File(path);

			if(!file.exists()) {
				file.createNewFile();
			}
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(file,true));
			
		    writer.write(message+ "\n");
		    
		    writer.close();
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}