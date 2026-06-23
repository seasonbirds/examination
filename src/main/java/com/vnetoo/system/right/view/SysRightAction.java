package com.vnetoo.system.right.view;

import javax.annotation.Resource;
import com.vnetoo.common.view.BaseAction;
import org.springframework.stereotype.Controller;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import com.vnetoo.system.right.bo.SysRight;
import com.vnetoo.system.right.service.SysRightService;

/**
 * @comment 系统操作资源表Action
 * @author chenh
 * @date 2013-11-18 
 */
@SuppressWarnings("serial") 
@Controller
@ParentPackage("default")
@Namespace("/")
@Action(value="sysRight",
results=
{
		@Result(name="list",	type="freemarker",location="sysRight/list.htm"),
		@Result(name="data",	type="freemarker",location="sysRight/data.htm"),	
		@Result(name="create",	type="freemarker",location="sysRight/create.htm"),
}
)
public class SysRightAction extends BaseAction<SysRight>{
	@Resource
	private SysRightService sysRightService;
	
	private SysRight sysRight = new SysRight();
	
	public void setSysRightService(SysRightService sysRightService) {
		this.sysRightService = sysRightService;
	}
	
	public SysRightService getSysRightService() {
		return sysRightService;
	}
	
	public void setSysRight(SysRight sysRight) {
		this.sysRight = sysRight;
	}
	
	public SysRight getSysRight() {
		return sysRight;
	}
	
	/**************************************************************************************/
		
	/**
	 * 进入查询系统操作资源表页面
	 * /jsp/sysRight/list.htm
	 */
	public String list() 
	{		
		search();
		return RESULT_LIST;
	}
		
	/**
	 * 查询系统操作资源表
	 * /jsp/sysRight/data.jsp
	 */
	public String search() 
	{				
		sysRightService.pageQuery(sysRight,paginator);
		return RESULT_DATA;
	}
	
	/**
	 * 进入系统操作资源表修改页面
	 * /jsp/sysRight/CreateSysRight.jsp 
	 */
	public String toCreateOrUpdate()
	{
		if(sysRight.getId()!=null&&sysRight.getId().longValue()>0){//修改
			sysRight=sysRightService.findById(sysRight.getId());			
		}
		return RESULT_CREATE;
	}
	
	/**
	 * 保存系统操作资源表	
	 */
	public String save()
	{
		if(sysRight.getId()!=null&&sysRight.getId().longValue()>0){
			SysRight po=sysRightService.findById(sysRight.getId());			
			//po.setName(sysRight.getName());
			
			//po.setUpdateBy(new Integer(0));			
			//sysRight=po;
		}
		else{
			//sysRight.setCreateBy(new Integer(0));
			//sysRight.setUpdateBy(sysRight.getCreateBy());
		}
		try{
			sysRightService.saveOrUpdate(sysRight);			
		}catch(Exception e){
			setResponseMsg(e.getMessage());
			setResponseFlag(-1);
		}
		writeResponse();
		return null;
	}
	
	/**
	 * 删除系统操作资源表
	 */
	public String remove() 
	{
		try{
			sysRightService.removeLogic(sysRight.getId());			
		}catch(Exception e){
			setResponseFlag(-1);
			setResponseMsg(e.getMessage());
		}
		writeResponse();
		return null;
	}
	
	/**
	 * 批量删除系统操作资源表
	 */
	public String batchRemove() 
	{
		try{
			for(int i=0;i<sysRight.getIds().length;i++){
				sysRightService.removeLogic(sysRight.getIds()[i]);		
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



