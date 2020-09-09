package cn.com.sgcc.generic;

public class Helper
{
	public static String replaceXmlEscapeChar(String text)
	{
		return text.replace("&", "&amp;")
			.replace("<", "&lt;")
			.replace(">", "&gt;");
	}

	public static String byteArrayToHexString(byte[] b)
	{
		if (null == b)
		{
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < b.length; i++)
		{
			String temp = Integer.toHexString(0xff & b[i]);
			if (temp.length() < 2)
			{
				sb.append('0');
			}
			sb.append(temp);
		}
		
		return sb.toString().toUpperCase();
	}

	public static byte[] hexStringToByteArray(String hex)
	{
		byte[] rt = null;

		if (null == hex || hex.equals(""))
		{
			return  null;
		}

		try
		{
			int offset = 0;
			if (hex.length() % 2 == 1)
			{
				rt = new byte[hex.length() / 2 + 1];
				rt[0] = (byte) Integer.parseInt(hex.substring(0, 1), 16);
				offset = 1;
			}
			else
			{
				rt = new byte[hex.length() / 2];
			}

			for (int i = 0; i + offset < rt.length; ++i)
			{
				rt[i + offset] = (byte) Integer.parseInt(hex.substring(2 * i + offset, 2 * i + offset + 2), 16);
			}
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
			return null;
		}

		return rt;
	}
}
