package com.vnetoo.webservice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HttpClient访问工具类，封装了GET、POST请求，认证和非认证的请求方式
 * @author Lisz
 *
 */
public class HttpClientUtil {
	private static final Logger log = LoggerFactory.getLogger(HttpClientUtil.class);
	
	private String charset = "UTF-8";
	
	private String targerHost;
	
	private int port;
	
	private String protocol;
	
	private String url;
	
	private String userName;
	
	private String password;
	
	public HttpClientUtil(){
		
	}
	
	public HttpClientUtil(String url, String userName, String password){
		this.url = url;
		this.userName = userName;
		this.password = password;
		
		if(url.indexOf("http") > -1){
			int proPoint = url.indexOf(":");
			protocol = url.substring(0, proPoint);
			String hostPort = url.substring(proPoint + 3, url.indexOf("/", proPoint + 3));
			if(hostPort.indexOf(":") > -1){
				String[] tmp = hostPort.split(":");
				targerHost = tmp[0];
				port = Integer.parseInt(tmp[1]);
			}else{
				targerHost = hostPort;
				port = 80;
			}
		}else{
			protocol = "http";
			String hostPort = url.substring(0, url.indexOf("/"));
			if(hostPort.indexOf(":") > -1){
				String[] tmp = hostPort.split(":");
				targerHost = tmp[0];
				port = Integer.parseInt(tmp[1]);
			}else{
				targerHost = hostPort;
				port = 80;
			}
		}
		
	}
	
	public HttpClientUtil(String url){
		this.url = url;
	}
	
	/**
	 * GET 请求
	 * @param authenticate 是否需要验证
	 * @return 请求结果字符串
	 */
	public String getRequest(boolean authenticate) {
		try{
			if(authenticate){
				if(userName == null || password == null){
					throw new Exception("User' name or password can't be null!");
				}
				return requestWithAuth();
			}else{
				return requestWithoutAuth();
			}
		}catch(Exception e){
			log.error("Server exception!", e);
		}
		return null;
	}
	
	/**
	 * POST请求
	 * @param authenticate	是否需要验证
	 * @param params	请求参数
	 * @return	请求结果字符串
	 */
	public String postRequset(boolean authenticate, Map<String, String> params){
		try{
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			if(params != null && !params.isEmpty()){
				Set<String> keySet = params.keySet(); 
				for(Iterator<String> it=keySet.iterator(); it.hasNext();){
					String key = it.next();
					String value = params.get(key);
					list.add(new BasicNameValuePair(key, value));
				}
			}
			
			if(authenticate){
				if(userName == null || password == null){
					throw new Exception("User' name or password can't be null!");
				}
				return postRequestwithAuth(list);
			}else{
				return postRequestWithoutAuth(list);
			}
		}catch(Exception e){
			log.error("Server exception!", e);
		}
		return null;
	}
	
	/**
	 *	无须验证的GET请求
	 * @return	请求结果字符串
	 * @throws Exception
	 */
	private String requestWithoutAuth() throws Exception{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = httpClient.execute(httpGet);
		
		String result = EntityUtils.toString(response.getEntity(), charset);
		response.close();
		httpClient.close();
		return result;
	}
	
	/**
	 * 需要验证的GET请求
	 * @return	请求结果字符串
	 * @throws Exception
	 */
	private String requestWithAuth() throws Exception{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpHost httpHost = new HttpHost(targerHost, port, protocol);
		HttpClientContext context = createAuthenticateContext(httpHost);
		
		HttpGet httpget = new HttpGet(url);
		CloseableHttpResponse  response = httpclient.execute(httpHost, httpget, context);
		String result = EntityUtils.toString(response.getEntity(), charset);
		response.close();
		httpclient.close();
		return result;
	}
	
	/**
	 * 需要验证的POST请求
	 * @param params		请求参数
	 * @return					请求结果字符串
	 * @throws Exception
	 */
	private String postRequestwithAuth(List<NameValuePair> params) throws Exception{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpHost httpHost = new HttpHost(targerHost, port, protocol);
		HttpClientContext context = createAuthenticateContext(httpHost);
		HttpPost httpPost = new HttpPost(url);
		if(params != null && !params.isEmpty()){
			httpPost.setEntity(new UrlEncodedFormEntity(params)); 
		}
		CloseableHttpResponse  response = httpclient.execute(httpHost, httpPost, context);
		String result = EntityUtils.toString(response.getEntity(), charset);
		response.close();
		return result;
	}
	
	/**
	 *  无须验证的POST请求
	 * @param params	请求参数
	 * @return	请求结果字符串
	 * @throws Exception
	 */
	private String postRequestWithoutAuth(List<NameValuePair> params) throws Exception{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);
		if(params != null && !params.isEmpty()){
			httppost.setEntity(new UrlEncodedFormEntity(params));
		}
		CloseableHttpResponse response = httpclient.execute(httppost);
		String result = EntityUtils.toString(response.getEntity(), charset);
		response.close();
		httpclient.close();
		return result;
	}
	
	/**
	 * 添加验证信息
	 * @param targetHost		主机信息
	 * @return
	 */
	private HttpClientContext createAuthenticateContext(HttpHost targetHost){
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(
		        new AuthScope(targetHost.getHostName(), targetHost.getPort()),
		        new UsernamePasswordCredentials(userName, password));

		// Create AuthCache instance
		AuthCache authCache = new BasicAuthCache();
		// Generate BASIC scheme object and add it to the local auth cache
		BasicScheme basicAuth = new BasicScheme();
		authCache.put(targetHost, basicAuth);

		// Add AuthCache to the execution context
		HttpClientContext context = HttpClientContext.create();
		context.setCredentialsProvider(credsProvider);
		context.setAuthCache(authCache);
		
		return context;
	}
}
