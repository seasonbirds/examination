/**
 * 
 */
package com.vnetoo.examination.login.view;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.vnetoo.common.ConfigKeys;
import com.vnetoo.common.annotation.CheckValidateCode;
import com.vnetoo.common.bo.IAuthUser;
import com.vnetoo.common.view.BaseAction;
import com.vnetoo.examination.SystemKeys;
import com.vnetoo.examination.enums.LoginResult;
import com.vnetoo.examination.login.service.LoginService;
import com.vnetoo.system.SystemAuthCache;
import com.vnetoo.system.right.bo.SysRight;
import com.vnetoo.system.user.bo.SysUser;

/**
 * @author chenh
 * @date 2013年8月14日
 */
@SuppressWarnings("serial")
@Controller
@ParentPackage("default")
@Namespace("/")
@Action(value="login",
results=
{
		@Result(name="login",			type="freemarker",location="login.htm"),
		@Result(name="success",			type="redirect",location="login!frame.action"),	
		@Result(name="frame",			type="freemarker",location="frame.htm"),
		@Result(name="login_redirect",	type="redirect",location="login.action"),
		@Result(name="moduleFrame", type="freemarker",location="moduleFrame.htm")
}
)
public class LoginAction extends BaseAction<SysUser>{
	private SysUser user;
	public SysUser getUser() {
		return user;
	}
	public void setUser(SysUser user) {
		this.user = user;
	}

	@Resource
	private LoginService loginService;
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
	
	@Resource
	private SystemAuthCache systemAuthCache;
	public void setSystemAuthCache(SystemAuthCache systemAuthCache) {
		this.systemAuthCache = systemAuthCache;
	}

	private static final String RESULT_FRAME = "frame";
	private static final String RESULT_LOGIN_REDIRECT = "login_redirect";
	
	private String moduleName;
	/**
	 * 打开登录页面
	 * @author chenh
	 * @date 2013年8月14日
	 * @return
	 */
	public String execute(){
		return LOGIN;
	}
	
	/**
	 * 登录
	 * @author chenh
	 * @date 2013年8月14日
	 * @return
	 */	
	@CheckValidateCode(ajaxRequest=false,errorPath=LOGIN)
	public String loginFromWeb(){		
		LoginResult result = loginService.login(user.getAcct(), user.getPwd());
		setResponseFlag(result.ordinal());
		if(result!=LoginResult.SUCCESS){
			setResponseMsg("账户或密码不正确！");
			if(result==LoginResult.ERROR_LOCK){
				int waitTime = 0;//this.loginService.getWaitTime(user.getAcct(), user.getPwd());//如果用户被锁定，查询所剩时间
				setResponseMsg("用户已锁定，请在"+waitTime+"分钟后进行登陆。");
			}
			//writeResponse();
			return LOGIN;
		}
		return SUCCESS;
	}
	
	
	
	public String logout(){
		IAuthUser u = super.getCurrentUser();
		if(u==null)
			return RESULT_LOGIN_REDIRECT;
		ActionContext.getContext().getSession().remove(ConfigKeys.AUTH_USRE);
		return RESULT_LOGIN_REDIRECT;
	}
	
	/**
	 * @author chenh
	 * @date 2013年8月19日
	 * @return
	 */
	public String frame(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		Assertion assertion = session != null ? (Assertion) session.getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION) : null;
		if(assertion!=null){
			user = (SysUser)assertion.getAttributes().get(SystemKeys.AUTH_USRE);
			if(user!=null && user.isStudent()){
				try {
					ServletActionContext.getResponse().sendRedirect("paperTest!search.action");
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
		}
		return RESULT_FRAME;
	}
	
	public String checkMenuRight()throws Exception{
		IAuthUser u=super.getCurrentUser();
		if(u==null){
			return RESULT_LOGIN_REDIRECT;
		}
		
		//权限判断
		SysUser authUser = (SysUser)u;
		SysRight right = systemAuthCache.checkSysRight(u.getId(),user.getMenuParentId(), user.getMenuId());
		if(right==null){
			return AUTH_ERROR;
		}
				
		authUser.setMenuParentId(user.getMenuParentId());
		authUser.setMenuId(user.getMenuId());
		
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request=ServletActionContext.getRequest();
		response.sendRedirect(request.getContextPath()+"/"+right.getUrl());	
		
		return null;
	}
	
	public String module(){
		return "moduleFrame";
	}
	
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
}
