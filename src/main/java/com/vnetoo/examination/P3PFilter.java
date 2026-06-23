package com.vnetoo.examination;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * P3P过滤器，在http的响应头部增加p3p支持，解决IE系列浏览器cookie失效问题
 */
public class P3PFilter implements Filter {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
		res.setHeader("P3P","CP=CAO PSA OUR");
		chain.doFilter(request, res);
	}

	@Override
	public void destroy() {
	}

}
