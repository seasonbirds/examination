package com.vnetoo.webservice;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.codec.Base64;

import com.alibaba.fastjson.JSONObject;
import com.vnetoo.common.AppContext;
import com.vnetoo.common.MD5;

/**
 * web service 调用安全拦截器：验证IP，用户名和密码
 */
public class IpAddrLimitAndAuthFilter implements Filter {
	private static final Logger log = LoggerFactory.getLogger(IpAddrLimitAndAuthFilter.class);
	
	private String charset = "UTF-8";
	
	private List<String> webServiceAllowIps;
	private Map<String, String>webServiceUserInfo;
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		//限制IP address
		String clientIpAddr = getClientIpAddr(httpRequest);
		if(!webServiceAllowIps.contains(clientIpAddr)){
			JSONObject json = new JSONObject();
			json.put("result", -1);
			json.put("msg", String.format("The ipAddress %s is not allowed!", clientIpAddr));
			httpResponse.setCharacterEncoding("UTF-8");
			httpResponse.getWriter().write(json.toJSONString());
			return;
		}
		
		//验证用户名密码
		try {
			String authHeader = httpRequest.getHeader("Authorization");
			if(authHeader != null && !"".equals(authHeader.trim()) && authHeader.startsWith("Basic ")){
					String[] userNamePasswd = extractAndDecodeHeader(authHeader);
					if(webServiceUserInfo.containsKey(userNamePasswd[0])){
						String passwd = webServiceUserInfo.get(userNamePasswd[0]);
						String md5Passwd = MD5.crypt(userNamePasswd[1]);
						if(md5Passwd.equalsIgnoreCase(passwd)){
							chain.doFilter(request, response);
						}
					}
			}
		} catch (Exception e) {
			log.error("Authenticate failure!", e);
		}
		JSONObject json = new JSONObject();
		json.put("result", -1);
		json.put("msg", String.format("Authenticate failure!", clientIpAddr));
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.getWriter().write(json.toJSONString());
	}
	
	/**
	 * 获取请求客户端的地址
	 * @param request		http请求
	 * @return					客户端ip地址
	 */
	private String getClientIpAddr(HttpServletRequest request){
		String ipAddress = request.getHeader("x-forwarded-for");
		if(ipAddress==null || ipAddress.length() == 0 || "unknown".equals(ipAddress) ){
			ipAddress = request.getHeader("Proxy-Client-IP");
			log.error("ipAddress  Proxy-Client-IP:  " + ipAddress);
		}
		if(ipAddress==null || ipAddress.length() == 0 || "unknown".equals(ipAddress) ){
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
			log.error("ipAddress  WL-Proxy-Client-IP:  " + ipAddress);
		}
		if(ipAddress==null || ipAddress.length() == 0 || "unknown".equals(ipAddress) ){
			ipAddress = request.getRemoteAddr();
			log.error("ipAddress  Remote-IP:  " + ipAddress);
		}
		if(ipAddress !=null && ipAddress.length() > 15){
			ipAddress = ipAddress.substring(0, 15);
		}
		return ipAddress;
	}
	
	/**
	 * 解码认证信息
	 * @param header	请求头字符串
	 * @return				认证信息的用户名密码数组
	 * @throws Exception
	 */
	private String[] extractAndDecodeHeader(String header) throws Exception{
        byte[] base64Token = header.substring(6).getBytes("UTF-8");
        byte[] decoded = Base64.decode(base64Token);

        String token = new String(decoded, charset);

        int delim = token.indexOf(":");
        if(delim < 0){
        	throw new Exception("Invalid basic authentication token");
        }
        return new String[] {token.substring(0, delim), token.substring(delim + 1)};
    }

	@SuppressWarnings("unchecked")
	public void init(FilterConfig filterConfig) throws ServletException {
		webServiceAllowIps = (List<String>) AppContext.getBean("webServiceAllowIps");
		webServiceUserInfo = (Map<String, String>) AppContext.getBean("webServiceUserInfo");
	}
	public void destroy() {
	}

}
