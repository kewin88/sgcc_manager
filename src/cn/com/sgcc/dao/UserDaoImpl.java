package cn.com.sgcc.dao;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import cn.com.sgcc.vo.*;
import cn.com.sgcc.ds.*;

public class UserDaoImpl implements UserDao
{
	public DataSource dataSource;

	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}
	public UserDaoImpl(){
		dataSource = new DataSourceImpl();
	}
	
	// may return null
	@Override
	public User select(int id) throws DaoException
	{
		User rt = null;
		Connection conn = null;
		try
		{
			String sql = "select id,name,password,userRole,userGroup from user where id=?";
			conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
			{
				rt = new User();
				rt.setId(rs.getInt(1));
				rt.setName(rs.getString(2));
				rt.setPassword(rs.getString(3));
				rt.setRole(rs.getString(4));
				rt.setGroup(rs.getString(5));
			}
			rs.close();
			stmt.close();
		} 
		catch (SQLException e)
		{
			throw new DaoException(e);
		} 
		finally
		{
			if (null != conn)
			{
				try
				{
					conn.close();
				}
				catch (SQLException e)
				{
					throw new DaoException(e);
				}
			}
		}
		return rt;
	}

	// may reutrn null
	@Override
	public User select(String name) throws DaoException
	{
		User rt = null;
		Connection conn = null;
		try
		{
			String sql = "select id,name,password,userRole,userGroup from user where name=?";
			conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
			{
				rt = new User();
				rt.setId(rs.getInt(1));
				rt.setName(rs.getString(2));
				rt.setPassword(rs.getString(3));
				rt.setRole(rs.getString(4));
				rt.setGroup(rs.getString(5));
			}
			rs.close();
			stmt.close();
		} 
		catch (SQLException e)
		{
			throw new DaoException(e);
		} 
		finally
		{
			if (null != conn)
			{
				try
				{
					conn.close();
				}
				catch (SQLException e)
				{
					throw new DaoException(e);
				}
			}
		}
		return rt;
	}

	// the size of list can be 0
	@Override
	public List<User> selectAll() throws DaoException
	{
		List<User> rt = new ArrayList<User>();
		Connection conn = null;
		try
		{
			String sql = "select id,name,password,userRole,userGroup from user";
			conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next())
			{
				User info = new User();
				info.setId(rs.getInt(1));
				info.setName(rs.getString(2));
				info.setPassword(rs.getString(3));
				info.setRole(rs.getString(4));
				info.setGroup(rs.getString(5));
				rt.add(info);
			}
			rs.close();
			stmt.close();
		} 
		catch (SQLException e)
		{
			throw new DaoException(e);
		} 
		finally
		{
			if (null != conn)
			{
				try
				{
					conn.close();
				}
				catch (SQLException e)
				{
					throw new DaoException(e);
				}
			}
		}
		return rt;
	}

	// ignore id; if name conflict, throw DaoException
	@Override
	public void insert(User info) throws DaoException
	{
		Connection conn = null;
		try
		{
			String sql = "insert into user(name,password,userRole,userGroup) values(?,?,?,?)";
			conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, info.getName());
			stmt.setString(2, info.getPassword());
			stmt.setString(3, info.getRole());
			stmt.setString(4, info.getGroup());
			stmt.execute();
			stmt.close();
		} 
		catch (SQLException e)
		{
			throw new DaoException(e);
		} 
		finally
		{
			if (null != conn)
			{
				try
				{
					conn.close();
				}
				catch (SQLException e)
				{
					throw new DaoException(e);
				}
			}
		}
	}

	// no such id or name conflict, throw DaoException
	@Override
	public void update(User info) throws DaoException
	{
		Connection conn = null;
		try
		{
			String sql = "update user set name=?,password=?,userRole=?,userGroup=? where id=?";
			conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, info.getName());
			stmt.setString(2, info.getPassword());
			stmt.setString(3, info.getRole());
			stmt.setString(4, info.getGroup());
			stmt.setInt(5, info.getId());
			stmt.execute();
			stmt.close();
		} 
		catch (SQLException e)
		{
			throw new DaoException(e);
		} 
		finally
		{
			if (null != conn)
			{
				try
				{
					conn.close();
				}
				catch (SQLException e)
				{
					throw new DaoException(e);
				}
			}
		}
	}

	// no such id, throw DaoException
	@Override
	public void delete(int id) throws DaoException
	{
		Connection conn = null;
		try
		{
			String sql = "delete from user where id=?";
			conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.execute();
			stmt.close();
		} 
		catch (SQLException e)
		{
			throw new DaoException(e);
		} 
		finally
		{
			if (null != conn)
			{
				try
				{
					conn.close();
				}
				catch (SQLException e)
				{
					throw new DaoException(e);
				}
			}
		}
	}
}
