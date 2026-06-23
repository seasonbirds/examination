/**
 * 
 */
package com.vnetoo.examination;



import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.vnetoo.common.ConfigKeys;
import com.vnetoo.common.bo.IAuthUser;
import com.vnetoo.system.user.bo.SysUser;

/**
 * @author chenh
 * @date 2013年8月29日
 */
@SuppressWarnings("serial")
public class AuthorityInterceptor extends AbstractInterceptor{

	public static final Logger logger = Logger.getLogger(AuthorityInterceptor.class);
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String className = invocation.getAction().getClass().getSimpleName();
		String method = invocation.getProxy().getMethod();
		logger.info("AuthorityInterceptor:"+className+":"+method+"()");
		
		//webservice请求不用验证
		StringBuffer requestUrl = ServletActionContext.getRequest().getRequestURL();
		if(requestUrl.indexOf("/services/") > -1 || requestUrl.toString().endsWith("login.action")){
			return invocation.invoke();
		}
		
		//单点登录场景下的验证
		HttpSession session = ServletActionContext.getRequest().getSession();
		Assertion assertion = session != null ? (Assertion) session.getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION) : null;
		if(assertion!=null){
			SysUser user = (SysUser)assertion.getAttributes().get(SystemKeys.AUTH_USRE);
			if(user != null){
				if(user.isStudent()){
					StringBuffer sb = ServletActionContext.getRequest().getRequestURL();
					String url = sb.substring(sb.lastIndexOf("/") + 1);
					if(RoleUrlUtil.hasStudentPermit(url)){
						return invocation.invoke();
					}else{
						//无权限
						logger.info("student has no permistion."+className+":"+method+"()");
						session.removeAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
						ServletActionContext.getResponse().sendRedirect("forbid.html");
						//return BaseAction.AUTH_ERROR;
						return null;
					}
				}else{
					//TODO留待做更详细的权限控制
					return invocation.invoke();
				}
			}else{
				ServletActionContext.getResponse().sendRedirect("forbid.html");
				return null;
			}
		}
		
		//非单点登录验证
		IAuthUser user = (IAuthUser)ServletActionContext.getRequest().getSession().getAttribute(ConfigKeys.AUTH_USRE);
		if(user != null){
			return invocation.invoke();
		}
		
		ServletActionContext.getResponse().sendRedirect("forbid.html");
		return null;
	}

}


