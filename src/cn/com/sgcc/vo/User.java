package cn.com.sgcc.vo;

public class User
{
	private int id;
	private String name;
	private String password;
	private String role;
	private String group;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getRole()
	{
		return role;
	}

	public void setRole(String role)
	{
		this.role = role;
	}

	public String getGroup()
	{
		return group;
	}

	public void setGroup(String group)
	{
		this.group = group;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("User:");
		sb.append("\n---id: " + id);
		sb.append("\n---name: " + name);
		sb.append("\n---password: " + password);
		sb.append("\n---role: " + role);
		sb.append("\n---group: " + group);
		sb.append("\n");

		return sb.toString();
	}
}
