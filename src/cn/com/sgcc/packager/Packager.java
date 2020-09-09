package cn.com.sgcc.packager;

import java.io.*;

public interface Packager
{
	public void pack(String rid, InputStream encryptedContent, OutputStream out) throws IOException;
}
