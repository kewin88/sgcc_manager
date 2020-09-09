package cn.com.sgcc.vo;

public class IpRange
{
	private int id;
	private String beginIp;
	private String endIp;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getBeginIp()
	{
		return beginIp;
	}

	public void setBeginIp(String beginIp)
	{
		this.beginIp = beginIp;
	}

	public String getEndIp()
	{
		return endIp;
	}

	public void setEndIp(String endIp)
	{
		this.endIp = endIp;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("IpRange:");
		sb.append("\n---id: " + id);
		sb.append("\n---beginIp: " + beginIp);
		sb.append("\n---endIp: " + endIp);
		sb.append("\n");

		return sb.toString();
	}
}
