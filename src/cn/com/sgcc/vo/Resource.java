package cn.com.sgcc.vo;

import cn.com.sgcc.generic.*;

public class Resource
{
	private int id;
	private String name;
	private String nameHash;
	private String key;
	private String iv;
	private String batchUuid;

	private Rights rights;

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

	public String getNameHash()
	{
		return nameHash;
	}

	public void setNameHash(String nameHash)
	{
		this.nameHash = nameHash;
	}

	public String getKey()
	{
		return key;
	}
	
	public void setKey(String key)
	{
		this.key = key;
	}

	public String getIv()
	{
		return iv;
	}

	public void setIv(String iv)
	{
		this.iv = iv;
	}

	public String getBatchUuid()
	{
		return batchUuid;
	}

	public void setBatchUuid(String batchUuid)
	{
		this.batchUuid = batchUuid;
	}

	public Rights getRights()
	{
		return rights;
	}

	public void setRights(Rights rights)
	{
		this.rights = rights;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Resource:");
		sb.append("\n---id: " + id);
		sb.append("\n---name: " + name);
		sb.append("\n---key: " + key);
		sb.append("\n---iv: " + iv);
		sb.append("\n");

		return sb.toString();
	}
}
