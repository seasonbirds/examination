/**
 * 
 */
package com.vnetoo.examination;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.IAssertionHandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vnetoo.common.AppContext;
import com.vnetoo.common.MD5;
import com.vnetoo.common.SystemGlobals;
import com.vnetoo.system.role.bo.SysRole;
import com.vnetoo.system.role.dao.SysRoleDAOImpl;
import com.vnetoo.system.user.bo.SysUser;
import com.vnetoo.system.user.dao.SysUserDAOImpl;
import com.vnetoo.webservice.HttpClientUtil;

/**
 * @author chenh
 * @date 2013年11月19日
 */
public class AssertionHandler implements IAssertionHandler{
	/* (non-Javadoc)
	 * @see org.jasig.cas.client.validation.IAssertionHandler#assertionHandler(org.jasig.cas.client.validation.Assertion)
	 */
	public void assertionHandler(Assertion assertion) {
		String acct = assertion.getPrincipal().getName();
		SysUserDAOImpl sysUserDAOImpl = (SysUserDAOImpl)AppContext.getBean("sysUserDAOImpl");
		SysUser user = new SysUser();
		user.setAcct(acct);
		List<SysUser> list = sysUserDAOImpl.selectList(user);
		//考试系统的注册用户，直接判断权限
		if(list != null && !list.isEmpty()){
			user = list.get(0);
			SysRoleDAOImpl sysRoleDAOImpl = (SysRoleDAOImpl)AppContext.getBean("sysRoleDAOImpl");
			List<SysRole> roleList = sysRoleDAOImpl.findRoleByUser(user.getId());
			int roleId = SystemGlobals.getValueInt(SystemKeys.STUDENT_ROLE_ID);
			if(roleList!=null && !roleList.isEmpty()){
				for(int i=0;i<roleList.size();i++){
					if(roleList.get(i).getId().intValue()==roleId){
						user.setStudent(true);
						break;
					}
				}
			}
			assertion.getAttributes().put(SystemKeys.AUTH_USRE, user);
			return;
		}
		//非考试系统用户（来源于其他业务系统的用户，学生用户），如果系统作为一个非完整子系统运行，
		//默认将其注册到考试系统
		if(!SystemGlobals.getValueBool(ExamConstant.INTEGRATED_RUNNING)){
			try{
				//系统作为一个单独的非完整子系统运行，部分权限控制由其他系统提供
				String fetchStudentInfoUrl = SystemGlobals.getValue(ExamConstant.FETCH_STUDENT_INFO_URL);
				if(fetchStudentInfoUrl != null && !"".equals(fetchStudentInfoUrl.trim())){
					//由其他系统获取学生信息
					HttpClientUtil util = new HttpClientUtil(fetchStudentInfoUrl);
					Map<String, String> params = new HashMap<String, String>();
					params.put("studentAcct", acct);
					String response = util.postRequset(false, params);
					if(response != null ){
						JSONObject json = JSON.parseObject(response);
						//将用户默认注册到考试系统
						if(json != null && json.containsKey("studentId") && json.containsKey("isStudent")){
							Integer suId = json.getInteger("studentId");
							user.setId(suId);
							user.setAcct(acct);
							user.setPwd(MD5.crypt(SystemGlobals.getValue(ExamConstant.INIT_PASSWORD)));
							user.setEmailActive("YES");
							user.setEnabledFlag(1);
							user.setStatus("USED");
							sysUserDAOImpl.insert(user);
							
							user.setId(suId);
							if(json.getBooleanValue("isStudent")){
								user.setStudent(json.getBooleanValue("isStudent"));
								//插入角色
								int roleId = SystemGlobals.getValueInt(SystemKeys.STUDENT_ROLE_ID);
								SysRoleDAOImpl sysRoleDAOImpl = (SysRoleDAOImpl)AppContext.getBean("sysRoleDAOImpl");
								Map<String, Integer> map = new HashMap<String, Integer>();
								map.put("roleId", roleId);
								map.put("userId", suId);
								sysRoleDAOImpl.insertUserRoleRel(map);
							}
							
							assertion.getAttributes().put(SystemKeys.AUTH_USRE, user);
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
