package cn.com.sgcc.dao;

import java.util.List;

import cn.com.sgcc.vo.*;

public interface ResourceDao
{
	// may return null
	public Resource select(int id) throws DaoException;

	// may return null
	public Resource select(String nameHash) throws DaoException;

	// the size of list can be 0
	public List<Resource> selectAll() throws DaoException;

	// ignore id; if name conflict, throw DaoException
	public void insert(Resource resource) throws DaoException;

	// no such id or name conflict, throw DaoException
	public void update(Resource resource) throws DaoException;

	// no such id, throw DaoException
	public void delete(int id) throws DaoException;
}
