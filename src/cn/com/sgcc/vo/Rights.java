package cn.com.sgcc.vo;

public class Rights
{
	private int id;
	private boolean onlineDisplayable;
	private boolean onlinePrintable;
	private boolean onlineCopyable;
	private boolean offlineDisplayable;
	private boolean offlinePrintable;
	private boolean offlineCopyable;
	private int offlineDisplayDuration = -1;
	private int offlineDisplayCount = -1;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public boolean getOnlineDisplayable()
	{
		return onlineDisplayable;
	}

	public void setOnlineDisplayable(boolean onlineDisplayable)
	{
		this.onlineDisplayable = onlineDisplayable;
	}

	public boolean getOnlinePrintable()
	{
		return onlinePrintable;
	}

	public void setOnlinePrintable(boolean onlinePrintable)
	{
		this.onlinePrintable = onlinePrintable;
	}

	public boolean getOnlineCopyable()
	{
		return onlineCopyable;
	}

	public void setOnlineCopyable(boolean onlineCopyable)
	{
		this.onlineCopyable = onlineCopyable;
	}

	public boolean getOfflineDisplayable()
	{
		return offlineDisplayable;
	}

	public void setOfflineDisplayable(boolean offlineDisplayable)
	{
		this.offlineDisplayable = offlineDisplayable;
	}

	public boolean getOfflinePrintable()
	{
		return offlinePrintable;
	}

	public void setOfflinePrintable(boolean offlinePrintable)
	{
		this.offlinePrintable = offlinePrintable;
	}

	public boolean getOfflineCopyable()
	{
		return offlineCopyable;
	}

	public void setOfflineCopyable(boolean offlineCopyable)
	{
		this.offlineCopyable = offlineCopyable;
	}

	public int getOfflineDisplayDuration()
	{
		return offlineDisplayDuration;
	}

	public void setOfflineDisplayDuration(int offlineDisplayDuration)
	{
		this.offlineDisplayDuration = offlineDisplayDuration;
	}

	public int getOfflineDisplayCount()
	{
		return offlineDisplayCount;
	}

	public void setOfflineDisplayCount(int offlineDisplayCount)
	{
		this.offlineDisplayCount = offlineDisplayCount;
	}
}
