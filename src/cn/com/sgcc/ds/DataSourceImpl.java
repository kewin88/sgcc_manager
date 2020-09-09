package cn.com.sgcc.ds;

import java.sql.*;

public class DataSourceImpl implements DataSource
{
	private String driver;
	private String url;
	private String user;
	private String password;
	public DataSourceImpl(){
		setDriver("com.mysql.jdbc.Driver");
		setUrl("jdbc:mysql://localhost/ac");
		setUser("root");
		setPassword("123123");
	}
	private boolean isInited = false;

	public void setDriver(String driver)
	{
		this.driver = driver;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public void setUser(String user)
	{
		this.user = user;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	private void init()
	{
		try
		{
			Class.forName(driver).newInstance();
			isInited = true;
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public Connection getConnection() throws SQLException
	{
		if (!isInited)
		{
			init();
		}

		return DriverManager.getConnection(url, user, password);
	}
}
