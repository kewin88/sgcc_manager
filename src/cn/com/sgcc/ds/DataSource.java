package cn.com.sgcc.ds;

import java.sql.*;

public interface DataSource
{
	public Connection getConnection() throws SQLException;
}
