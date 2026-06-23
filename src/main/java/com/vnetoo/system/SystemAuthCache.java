/**
 * 
 */
package com.vnetoo.system;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.springframework.stereotype.Service;

import com.vnetoo.common.AppContext;
import com.vnetoo.common.enums.Enabled;
import com.vnetoo.system.right.bo.SysRight;
import com.vnetoo.system.right.dao.SysRightDAO;
import com.vnetoo.system.role.bo.SysRole;
import com.vnetoo.system.role.dao.SysRoleDAO;

/**
 * @author chenh
 * @date 2013年8月21日
 */
@Service
public class SystemAuthCache {
	
	@Resource(name="systemAuthCacheFactory")
	private Cache cache;  	  
    public void setCache(Cache cache) {  
        this.cache = cache;  
    }
    
    @Resource
    private SysRoleDAO sysRoleDAO;
    public void setSysRoleDAO(SysRoleDAO sysRoleDAO) {
		this.sysRoleDAO = sysRoleDAO;
	}
	
	@Resource
    private SysRightDAO sysRightDAO;
	public void setSysRightDAO(SysRightDAO sysRightDAO) {
		this.sysRightDAO = sysRightDAO;
	}
	
	private String roleKey(Integer roleId){
		return "Role"+roleId;
	}
	private String userKey(Integer userId){
		return "User"+userId;
	}
	private String rightKey(Integer rightId){
		return "Right"+rightId;
	}

	/**
     * 缓存数据
     * @author chenh
     * @date 2013年8月22日
     */
    public void load(){    	
    	List<SysRole> roleList = sysRoleDAO.selectList(new SysRole());
    	for (Iterator<SysRole> iterator = roleList.iterator(); iterator.hasNext();) {
			SysRole sysRole = (SysRole) iterator.next();
			List<SysRight> rightList = sysRightDAO.findRightByRole(sysRole.getId());
			Element ele = new Element(roleKey(sysRole.getId()), rightList);
			cache.put(ele);
		}
    	
    	List<SysRight> rightList = sysRightDAO.selectList(new SysRight());
    	for (Iterator<SysRight> iterator = rightList.iterator(); iterator.hasNext();) {
			SysRight sysRight = (SysRight) iterator.next();
			Element ele = new Element(rightKey(sysRight.getId()), sysRight);
			cache.put(ele);
		}
    }
    
    /**
     * @author chenh
     * @date 2013年8月22日
     */
    public static void reload(){
    	SystemAuthCache systemAuthCache = (SystemAuthCache)AppContext.getBean("systemAuthCache");
    	systemAuthCache.load();
    }
    
    /**
     * 获取用户操作资源一级菜单
     * @author chenh
     * @date 2013年8月22日
     * @param userId
     * @return
     */
    public List<SysRight> findRootRightByUser(Integer userId){
    	List<SysRight> righList = findAllRightByUser(userId);
    	List<SysRight> result = new ArrayList<SysRight>();
    	for (Iterator<SysRight> iterator = righList.iterator(); iterator.hasNext();) {
			SysRight sysRight = (SysRight) iterator.next();
			if(sysRight.getParentId()==null||sysRight.getParentId().intValue()==0)
				result.add(sysRight);
		}
    	return result;
    }
    
    /**
	 * 获取用户操作资源下的子操作资源
	 * @author chenh
	 * @date May 30, 2012
	 * @param userId
	 * @param sysRightId
	 * @return
	 * @throws Exception
	 */
	public List<SysRight> getMenuList(Integer userId,Integer rightId){
		List<SysRight> righList = findAllRightByUser(userId);
    	List<SysRight> result = new ArrayList<SysRight>();
    	for (Iterator<SysRight> iterator = righList.iterator(); iterator.hasNext();) {
			SysRight sysRight = (SysRight) iterator.next();
			if(sysRight.getParentId()!=null&&sysRight.getParentId().intValue()==rightId)
				result.add(sysRight);
		}    	
    	return result;
	}
    
    /**
     * 根据用户搜索用户的菜单
     * @author chenh
     * @date 2013年8月22日
     * @param userId
     * @return
     */
    @SuppressWarnings("unchecked")
	private List<SysRight> findAllRightByUser(Integer userId){
    	List<SysRole> roleList = findRoleListByUser(userId);
    	List<SysRight> righList = new ArrayList<SysRight>();
    	for (Iterator<SysRole> iterator = roleList.iterator(); iterator.hasNext();) {
			SysRole sysRole = (SysRole) iterator.next();
			Element rightElement = cache.get(roleKey(sysRole.getId()));
			if(rightElement==null)
				continue;
			righList.addAll((List<SysRight>)rightElement.getValue());
		}
    	return righList;
    }
    
    /**
     * 根据用户搜索用户的角色
     * @author chenh
     * @date 2013年8月22日
     * @param userId
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<SysRole> findRoleListByUser(Integer userId){
    	Element roleElement = cache.get(userKey(userId));
    	if(roleElement==null){
    		List<SysRole> roleList = sysRoleDAO.findRoleByUser(userId);
    		roleElement = new Element(userKey(userId),roleList);
    		cache.put(roleElement);
    	}
    	return (List<SysRole>)roleElement.getValue();
    }
    
    /**
	 * 判断用户是否有操作权限（两级操作）,有返回当前操作资源，没有返回null
	 * @author chenh
	 * @date Jun 6, 2012
	 * @param userId
	 * @param menuParentId
	 * @param menuId
	 * @return
	 */
    @Deprecated
	public SysRight checkSysRight(Integer userId,Integer menuParentId,Integer menuId)throws Exception{
		Element ele = cache.get(rightKey(menuId));
		if(ele==null)
			return null;
		
		SysRight right = (SysRight)ele.getValue();
		if(right.getParentId().intValue()!=menuParentId.intValue())
			return null;
		
		List<SysRight> rightList = findAllRightByUser(userId);
		if(rightList==null || rightList.isEmpty())
			return null;
		
		Iterator<SysRight> list = rightList.iterator();
		while (list.hasNext()) {
			SysRight o = (SysRight) list.next();
			if (o != null && o.getId().intValue() == menuId.intValue() 
					&& o.getEnabledFlag().intValue() == 1
					&& o.getStatus() == Enabled.USED) {
				return o;
			}
		}
		return null;
	}
}
