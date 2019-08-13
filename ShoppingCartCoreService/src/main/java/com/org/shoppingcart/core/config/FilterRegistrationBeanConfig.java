package com.org.shoppingcart.core.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class FilterRegistrationBeanConfig {

	@Bean
	public FilterRegistrationBean<OncePerRequestFilter> executionTimeLoggingFilter() {
		return new FilterRegistrationBean<OncePerRequestFilter>() {
			{
				setOrder(OrderedFilter.REQUEST_WRAPPER_FILTER_MAX_ORDER);
				setFilter(new OncePerRequestFilter() {
					@Override
					protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
							FilterChain filterChain) throws ServletException, IOException {
						StopWatch watch = new StopWatch();
						watch.start();
						try {
							filterChain.doFilter(request, response);
						} finally {
							watch.stop();
							log.info("Shopping Cart Service Request -> {} : {} : completed within {} ms",
									getUriWithMethodAndQuery(request), watch.getTotalTimeMillis());
						}
					}

					private String getUriWithMethodAndQuery(HttpServletRequest request) {
						return request.getMethod() + ": " + request.getRequestURI()
								+ (StringUtils.hasText(request.getQueryString()) ? "?" + request.getQueryString() : "");
					}
				});
			}
		};
	}

}
