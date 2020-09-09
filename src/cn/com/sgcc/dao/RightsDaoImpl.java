package cn.com.sgcc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.com.sgcc.ds.DataSource;
import cn.com.sgcc.ds.DataSourceImpl;
import cn.com.sgcc.vo.*;

public class RightsDaoImpl implements RightsDao {

	public DataSource dataSource;

	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}
	public RightsDaoImpl(){
		dataSource = new DataSourceImpl();
	}
	
	
	// the size of list can be 0
	@Override
	public List<Rights> selectAll() throws DaoException
	{
		List<Rights> rt = new ArrayList<Rights>();
		Connection conn = null;
		try
		{
			String sql = "select id,onlineDisplayable,onlinePrintable,onlineCopyable,offlineDisplayable,offlineDisplayDuration,offlineDisplayCount from rights";
			conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next())
			{
				Rights info = new Rights();
				info.setId(rs.getInt(1));
				info.setOnlineDisplayable(rs.getBoolean(2));
				info.setOnlinePrintable(rs.getBoolean(3));
				info.setOnlineCopyable(rs.getBoolean(4));
				info.setOfflineDisplayable(rs.getBoolean(5));
				info.setOfflineDisplayDuration(rs.getInt(6));
				info.setOfflineDisplayCount(rs.getInt(7));
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
	public void insert(Rights info) throws DaoException
	{
		Connection conn = null;
		try
		{
			String sql = "insert into rights(onlineDisplayable,onlinePrintable,onlineCopyable,offlineDisplayable,offlineDisplayDuration,offlineDisplayCount ) values(?,?,?,?,?,?)";
			conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setBoolean(1, info.getOnlineDisplayable());
			stmt.setBoolean(2, info.getOnlinePrintable());
			stmt.setBoolean(3, info.getOnlineCopyable());
			stmt.setBoolean(4, info.getOfflineDisplayable());
			stmt.setInt(5, info.getOfflineDisplayDuration());
			stmt.setInt(6, info.getOfflineDisplayCount());
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
	public void delete() throws DaoException
	{
		Connection conn = null;
		try
		{
			String sql = "delete from rights";
			conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
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
