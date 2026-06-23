package com.vnetoo.examination;

import java.util.HashMap;
import java.util.Map;

import com.vnetoo.common.SystemGlobals;

/**
 * 角色和请求url关系类，起控制角色权限作用
 */
public class RoleUrlUtil {

	private static Map<String, String> studentRoleUrlMap = new HashMap<String, String>();
	
	public static void init() throws Exception{
		String studentAuth = SystemGlobals.getValue(ExamConstant.STUDENT_AUTH);
		if(studentAuth == null || "".equals(studentAuth.trim())){
			throw new Exception("The configuration must be contains 'student_auth' and it's can't ne null.");
		}
		
		String[] auths = studentAuth.split(",");
		for(String auth : auths){
			studentRoleUrlMap.put(auth, "");
		}
		
	}
	
	public static boolean hasStudentPermit(String url){
		return studentRoleUrlMap.containsKey(url);
	}
	
}
