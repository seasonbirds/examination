package com.vnetoo.system.role.view;

import javax.annotation.Resource;
import com.vnetoo.common.view.BaseAction;
import org.springframework.stereotype.Controller;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import com.vnetoo.system.role.bo.SysRole;
import com.vnetoo.system.role.service.SysRoleService;

/**
 * @comment 角色Action
 * @author chenh
 * @date 2014-01-15 
 */
@SuppressWarnings("serial") 
@Controller
@ParentPackage("default")
@Namespace("/")
@Action(value="sysRole",
results=
{
		@Result(name="search",	type="freemarker",location="sysRole/search.htm"),
		@Result(name="list",	type="freemarker",location="sysRole/list.htm"),	
		@Result(name="edit",	type="freemarker",location="sysRole/edit.htm"),
		@Result(name="show",	type="freemarker",location="sysRole/show.htm"),
}
)
public class SysRoleAction extends BaseAction<SysRole>{
	@Resource
	private SysRoleService sysRoleService;
	
	private SysRole sysRole = new SysRole();
	
	public void setSysRoleService(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
	}
	
	public SysRoleService getSysRoleService() {
		return sysRoleService;
	}
	
	public void setSysRole(SysRole sysRole) {
		this.sysRole = sysRole;
	}
	
	public SysRole getSysRole() {
		return sysRole;
	}
	
	/**************************************************************************************/
		
	/**
	 * 进入查询角色页面
	 * /htm/sysRole/search.htm
	 */
	public String search() 
	{		
		query();
		return RESULT_SEARCH;
	}
		
	/**
	 * 查询角色
	 * /htm/sysRole/list.htm
	 */
	public String query() 
	{				
		sysRoleService.pageQuery(sysRole,paginator);
		return RESULT_LIST;
	}
	
	/**
	 * 进入角色修改页面
	 * /htm/sysRole/edit.htm 
	 */
	public String toCreateOrUpdate()
	{
		if(sysRole.getId()!=null&&sysRole.getId().longValue()>0){//修改
			sysRole=sysRoleService.findById(sysRole.getId());			
		}
		return RESULT_EDIT;
	}
	
	/**
	 * 进入角色查看页面
	 * /htm/sysRole/show.htm 
	 */
	public String show()
	{
		toCreateOrUpdate();
		return RESULT_SHOW;
	}
	
	/**
	 * 保存角色	
	 */
	public String save()
	{
		if(sysRole.getId()!=null&&sysRole.getId().longValue()>0){
			SysRole po=sysRoleService.findById(sysRole.getId());			
			//po.setName(sysRole.getName());
			
			po.setName(sysRole.getName());
			po.setDescription(sysRole.getDescription());
			po.setParentId(sysRole.getParentId());
			po.setStatus(sysRole.getStatus());
			sysRole=po;
		}
		else{
			//sysRole.setCreateBy(new Integer(0));
			//sysRole.setUpdateBy(sysRole.getCreateBy());
		}
		try{
			sysRoleService.saveOrUpdate(sysRole);			
		}catch(Exception e){
			setResponseMsg(e.getMessage());
			setResponseFlag(-1);
		}
		writeResponse();
		return null;
	}
	
	/**
	 * 删除角色
	 */
	public String remove() 
	{
		try{
			sysRoleService.removeLogic(sysRole.getId());			
		}catch(Exception e){
			setResponseFlag(-1);
			setResponseMsg(e.getMessage());
		}
		writeResponse();
		return null;
	}
	
	/**
	 * 批量删除角色
	 */
	public String batchRemove() 
	{
		try{
			for(int i=0;i<sysRole.getIds().length;i++){
				sysRoleService.removeLogic(sysRole.getIds()[i]);		
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



