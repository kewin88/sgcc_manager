package cn.com.sgcc.crypto;

import java.util.Vector;

public class EncryptResult
{
	public static final int FAIL        = 0;
	public static final int NEW_ENCRYPT = 1;
	public static final int OLD_ENCRYPT = 2;
	public static final int RE_ENCRYPT  = 3;
	public int NumberOfAll;
	public int NumberOfFail;
	public int NumberOfNewEncrypt;
	public int NumberOfOldEncrypt;
	public int NumberOfReEncrypt;
	public int CurrentStatus;
	public String CurrentFilename;
	public Vector FailedFiles;
}
