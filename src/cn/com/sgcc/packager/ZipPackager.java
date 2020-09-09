package cn.com.sgcc.packager;

import java.io.*;
import java.util.zip.*;

public class ZipPackager implements Packager
{
	public static final int BLOCK_SIZE = 4 * 1024;

	private String acUrl;

	public void setAcUrl(String acUrl)
	{
		this.acUrl = acUrl;
	}

	@Override
	public void pack(String rid, InputStream encryptedContent, OutputStream out) throws IOException
	{
		ZipOutputStream zouts = new ZipOutputStream(out);

		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<header>");
		sb.append("<FileID>");
		sb.append(rid);
		sb.append("</FileID>");
		sb.append("<AccessControlServerUrl>");
		sb.append(acUrl);
		sb.append("</AccessControlServerUrl>");
		sb.append("</header>");
		byte[] headerBytes = sb.toString().getBytes();

		{
			ZipEntry headerEntry = new ZipEntry("header.xml");
			zouts.putNextEntry(headerEntry);
			zouts.write(headerBytes, 0, headerBytes.length);
			zouts.flush();
		}

		{
			ZipEntry encryptedContentEntry = new ZipEntry("encrypted");
			zouts.putNextEntry(encryptedContentEntry);
			int len = -1;
			byte[] buffer = new byte[BLOCK_SIZE];
			while ((len = encryptedContent.read(buffer)) != -1)
			{
				zouts.write(buffer, 0, len);
				zouts.flush();
			}
		}

		zouts.finish();// not close, or else it will close 'out' too.
	}
}
