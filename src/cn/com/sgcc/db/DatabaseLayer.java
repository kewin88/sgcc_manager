package cn.com.sgcc.db;

import cn.com.sgcc.dao.*;

public class DatabaseLayer
{
	private UserDao userDao;
	private ResourceDao resourceDao;
	private IpRangeDao ipRangeDao;
	private RightsDao rightsDao;
	public DatabaseLayer(){
		userDao = new UserDaoImpl();
		ipRangeDao = new IpRangeDaoImpl();
		resourceDao = new ResourceDaoImpl();
		rightsDao = new RightsDaoImpl();
	}
	public UserDao getUserDao()
	{
		return userDao;
	}

	public void setUserDao(UserDao userDao)
	{
		this.userDao = userDao;
	}
	
	public ResourceDao getResourceDao()
	{
		return resourceDao;
	}

	public void setResourceDao(ResourceDao resourceDao)
	{
		this.resourceDao = resourceDao;
	}

	public IpRangeDao getIpRangeDao()
	{
		return ipRangeDao;
	}

	public void setIpRangeDao(IpRangeDao ipRangeDao)
	{
		this.ipRangeDao = ipRangeDao;
	}
	public RightsDao getRightsDao() {
		return rightsDao;
	}
	public void setRightsDao(RightsDao rightsDao) {
		this.rightsDao = rightsDao;
	}
	
}
