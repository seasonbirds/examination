package com.vnetoo.system.user.view;

import javax.annotation.Resource;
import com.vnetoo.common.view.BaseAction;
import org.springframework.stereotype.Controller;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import com.vnetoo.system.user.bo.SysUser;
import com.vnetoo.system.user.service.SysUserService;

/**
 * @comment 用户信息表Action
 * @author chenh
 * @date 2013-11-18 
 */
@SuppressWarnings("serial") 
@Controller
@ParentPackage("default")
@Namespace("/")
@Action(value="sysUser",
results=
{
		@Result(name="list",	type="freemarker",location="sysUser/list.htm"),
		@Result(name="data",	type="freemarker",location="sysUser/data.htm"),	
		@Result(name="create",	type="freemarker",location="sysUser/create.htm"),
}
)
public class SysUserAction extends BaseAction<SysUser>{
	@Resource
	private SysUserService sysUserService;
	
	private SysUser sysUser = new SysUser();
	
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}
	
	public SysUserService getSysUserService() {
		return sysUserService;
	}
	
	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}
	
	public SysUser getSysUser() {
		return sysUser;
	}
	
	/**************************************************************************************/
		
	/**
	 * 进入查询用户信息表页面
	 * /jsp/sysUser/list.htm
	 */
	public String list() 
	{		
		search();
		return RESULT_LIST;
	}
		
	/**
	 * 查询用户信息表
	 * /jsp/sysUser/data.jsp
	 */
	public String search() 
	{				
		sysUserService.pageQuery(sysUser,paginator);
		return RESULT_DATA;
	}
	
	/**
	 * 进入用户信息表修改页面
	 * /jsp/sysUser/CreateSysUser.jsp 
	 */
	public String toCreateOrUpdate()
	{
		if(sysUser.getId()!=null&&sysUser.getId().longValue()>0){//修改
			sysUser=sysUserService.findById(sysUser.getId());			
		}
		return RESULT_CREATE;
	}
	
	/**
	 * 保存用户信息表	
	 */
	public String save()
	{
		if(sysUser.getId()!=null&&sysUser.getId().longValue()>0){
			SysUser po=sysUserService.findById(sysUser.getId());			
			//po.setName(sysUser.getName());
			
			//po.setUpdateBy(new Integer(0));			
			//sysUser=po;
		}
		else{
			//sysUser.setCreateBy(new Integer(0));
			//sysUser.setUpdateBy(sysUser.getCreateBy());
		}
		try{
			sysUserService.saveOrUpdate(sysUser);			
		}catch(Exception e){
			setResponseMsg(e.getMessage());
			setResponseFlag(-1);
		}
		writeResponse();
		return null;
	}
	
	/**
	 * 删除用户信息表
	 */
	public String remove() 
	{
		try{
			sysUserService.removeLogic(sysUser.getId());			
		}catch(Exception e){
			setResponseFlag(-1);
			setResponseMsg(e.getMessage());
		}
		writeResponse();
		return null;
	}
	
	/**
	 * 批量删除用户信息表
	 */
	public String batchRemove() 
	{
		try{
			for(int i=0;i<sysUser.getIds().length;i++){
				sysUserService.removeLogic(sysUser.getIds()[i]);		
			}
		}catch(Exception e){
			setResponseFlag(-1);
			setResponseMsg(e.getMessage());
		}
		writeResponse();
		return null;
	}
	/**************************************************************************************/
}



