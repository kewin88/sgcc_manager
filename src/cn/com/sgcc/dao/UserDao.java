package cn.com.sgcc.dao;

import java.util.List;

import cn.com.sgcc.vo.*;

public interface UserDao
{
	// may return null
	public User select(int id) throws DaoException;

	// may reutrn null
	public User select(String name) throws DaoException;

	// the size of list can be 0
	public List<User> selectAll() throws DaoException;

	// ignore id; if name conflict, throw DaoException
	public void insert(User user) throws DaoException;

	// no such id or name conflict, throw DaoException
	public void update(User user) throws DaoException;

	// no such id, throw DaoException
	public void delete(int id) throws DaoException;
}
