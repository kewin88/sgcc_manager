package cn.com.sgcc.dao;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import cn.com.sgcc.vo.*;
import cn.com.sgcc.ds.*;

public class IpRangeDaoImpl implements IpRangeDao
{
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}
	public IpRangeDaoImpl(){
		dataSource = new DataSourceImpl();
	}
	
	// may return null
	@Override
	public IpRange select(int id) throws DaoException
	{
		IpRange rt = null;
		Connection conn = null;
		try
		{
			String sql = "select id,inet_ntoa(beginIp),inet_ntoa(endIp) from ipRange where id=?";
			conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
			{
				rt = new IpRange();
				rt.setId(rs.getInt(1));
				rt.setBeginIp(rs.getString(2));
				rt.setEndIp(rs.getString(3));
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
	public List<IpRange> selectAll() throws DaoException
	{
		List<IpRange> rt = new ArrayList<IpRange>();
		Connection conn = null;
		try
		{
			String sql = "select id,inet_ntoa(beginIp),inet_ntoa(endIp) from ipRange";
			conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next())
			{
				IpRange info = new IpRange();
				info.setId(rs.getInt(1));
				info.setBeginIp(rs.getString(2));
				info.setEndIp(rs.getString(3));
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

	// ip illegal, throw DaoException
	@Override
	public void insert(IpRange info) throws DaoException
	{
		Connection conn = null;
		try
		{
			String sql = "insert into ipRange(beginIp,endIp) values(inet_aton(?),inet_aton(?))";
			conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, info.getBeginIp());
			stmt.setString(2, info.getEndIp());
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

	// no such id or ip illegal, throw DaoException
	@Override
	public void update(IpRange info) throws DaoException
	{
		Connection conn = null;
		try
		{
			String sql = "update ipRange set beginIp=inet_aton(?),endIp=inet_aton(?) where id=?";
			conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, info.getBeginIp());
			stmt.setString(2, info.getEndIp());
			stmt.setInt(3, info.getId());
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
			String sql = "delete from ipRange where id=?";
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

	// return true if ip is in any ipRange
	@Override
	public boolean isValidIp(String ip) throws DaoException
	{
		boolean rt = false;
		Connection conn = null;
		try
		{
			String sql = "select id from ipRange where inet_aton(?) between beginIp and endIp";
			conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, ip);
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
			{
				rt = true;
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
}
