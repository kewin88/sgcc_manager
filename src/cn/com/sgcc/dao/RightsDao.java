package cn.com.sgcc.dao;

import java.util.List;

import cn.com.sgcc.vo.*;

public interface RightsDao {

	// the size of list can be 0
		public List<Rights> selectAll() throws DaoException;

		// ignore id; if name conflict, throw DaoException
		public void insert(Rights rights) throws DaoException;
		
		// no such id, throw DaoException
		public void delete() throws DaoException;
}
