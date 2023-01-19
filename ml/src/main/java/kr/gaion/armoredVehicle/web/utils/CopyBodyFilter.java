package kr.gaion.armoredVehicle.web.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class CopyBodyFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		try {
		      ReadableRequestWrapper wrapper = new ReadableRequestWrapper((HttpServletRequest) request);
		      wrapper.setAttribute("requestBody", wrapper.getRequestBody());
		      chain.doFilter(wrapper, response);
		    } catch (Exception e) {
		      chain.doFilter(request, response);
		    }
	}

}
