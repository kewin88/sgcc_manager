package cn.com.sgcc.dao;

import java.util.List;

import cn.com.sgcc.vo.*;

public interface IpRangeDao
{
	// may return null
	public IpRange select(int id) throws DaoException;

	// the size of list can be 0
	public List<IpRange> selectAll() throws DaoException;

	// ip illegal, throw DaoException
	public void insert(IpRange ipRange) throws DaoException;

	// no such id or ip illegal, throw DaoException
	public void update(IpRange ipRange) throws DaoException;

	// no such id, throw DaoException
	public void delete(int id) throws DaoException;

	// return true if ip is in any ipRange
	public boolean isValidIp(String ip) throws DaoException;
}
