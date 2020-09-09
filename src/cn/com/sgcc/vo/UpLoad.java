package cn.com.sgcc.vo;

import java.io.*;
import java.security.*;
import java.util.Vector;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.*;

import org.apache.commons.logging.*;

import cn.com.sgcc.vo.*;
import cn.com.sgcc.crypto.EncryptResult;
import cn.com.sgcc.db.*;
import cn.com.sgcc.dao.*;
import cn.com.sgcc.packager.*;
import cn.com.sgcc.generic.*;
import cn.com.sgcc.inform.*;

public class UpLoad 
{
	private static Log logger = LogFactory.getLog(UpLoad.class);

	private String cryptoAlgorithm;
	private String cryptoMode;
	private String cryptoPadding;
	private int cryptoKeySize;
	private int cryptoIvSize;

	private String sourceSuffix=".gwbz";
	private String destinationSuffix;

	private Packager packager;

	private DatabaseLayer databaseLayer;

	public void setCryptoAlgorithm(String cryptoAlgorithm)
	{
		this.cryptoAlgorithm = cryptoAlgorithm;
	}

	public void setCryptoMode(String cryptoMode)
	{
		this.cryptoMode = cryptoMode;
	}

	public void setCryptoPadding(String cryptoPadding)
	{
		this.cryptoPadding = cryptoPadding;
	}

	public void setCryptoKeySize(int cryptoKeySize)
	{
		this.cryptoKeySize = cryptoKeySize;
	}


	public void setCryptoIvSize(int cryptoIvSize)
	{
		this.cryptoIvSize = cryptoIvSize;
	}

	public void setSourceSuffix(String sourceSuffix)
	{
		this.sourceSuffix = sourceSuffix;
	}

	public void setDestinationSuffix(String destinationSuffix)
	{
		this.destinationSuffix = destinationSuffix;
	}

	public void setPackager(Packager packager)
	{
		this.packager = packager;
	}

	public void setDatabaseLayer(DatabaseLayer databaseLayer)
	{
		this.databaseLayer = databaseLayer;
	}


	public int encryptFile(File fromFile, boolean force)
	{
		if (!fromFile.exists())
		{
			logger.error(new StringBuilder().append(fromFile.getPath()).append(" not exists").toString());
			return EncryptResult.FAIL;
		}
		if (!fromFile.isFile())
		{
			logger.error(new StringBuilder().append(fromFile.getPath()).append(" is not a file").toString());
			return EncryptResult.FAIL;
		}
		if (!fromFile.canRead())
		{
			logger.error(new StringBuilder().append(fromFile.getPath()).append(" can not read").toString());
			return EncryptResult.FAIL;
		}
		if (!(fromFile.getName().endsWith(sourceSuffix.toLowerCase()) || fromFile.getName().endsWith(sourceSuffix.toUpperCase())))
		{
			logger.error(new StringBuilder().append(fromFile.getPath()).append(" not ends with ").append(sourceSuffix).toString());
			return EncryptResult.FAIL;
		}



//		String fromFilename = fromFile.getName();
//		String filename = fromFilename.substring(0, fromFilename.length() - sourceSuffix.length());
//		String toFilename = filename + destinationSuffix;
//		File toFile = new File(toDir, toFilename);
//
//		System.out.println(fromFilename);
//		InputStream encryptedContent = null;
//		OutputStream toFileOuts = null;
//
//		int currentStatus = EncryptResult.FAIL;
//
//		try
//		{
//			// file name hash
//			MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
//			String filenameHash = Helper.byteArrayToHexString(sha1.digest(filename.getBytes("utf-8")));
//
//			Resource resource = databaseLayer.getResourceDao().select(filenameHash);
//			if (null != resource && !force) // old-encrypt
//			{
//				currentStatus = EncryptResult.OLD_ENCRYPT;
//				logger.info(new StringBuilder().append("[old-encrypt] ").append(fromFile.getPath()).toString());
//			}
//			else // new-encrypt or re-encrypt
//			{
//				// CipherInputStream
//				byte[] key = new byte[cryptoKeySize/8];
//				byte[] iv = new byte[cryptoIvSize/8];
//				SecureRandom sr = new SecureRandom();
//				sr.nextBytes(key);
//				sr.nextBytes(iv);
//				SecretKeySpec skSpec = new SecretKeySpec(key, cryptoAlgorithm);
//				IvParameterSpec ivSpec = new IvParameterSpec(iv);
//				javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(cryptoAlgorithm + "/" + cryptoMode + "/" + cryptoPadding);
//				cipher.init(Cipher.ENCRYPT_MODE, skSpec, ivSpec);
//				encryptedContent = new javax.crypto.CipherInputStream(new FileInputStream(fromFile), cipher);
//
//				// FileOutputStream
//				toFileOuts = new FileOutputStream(toFile);
//
//				// pack and write .gwbz 
//				packager.pack(filenameHash, encryptedContent, toFileOuts);
//
//				// write database
//				if (null == resource) // new-encrypt
//				{
//					resource = new Resource();
//					resource.setName(new String(filename.getBytes("utf-8"), "utf-8"));
//					resource.setNameHash(filenameHash);
//					resource.setKey(Helper.byteArrayToHexString(key));
//					resource.setIv(Helper.byteArrayToHexString(iv));
//					resource.setRights(rights);
//
//					databaseLayer.getResourceDao().insert(resource);
//
//					currentStatus = EncryptResult.NEW_ENCRYPT;
//					logger.info(new StringBuilder().append("[new-encrypt] ").append(fromFile.getPath()).toString());
//				}
//				else // re-encrypt
//				{
//					resource.setKey(Helper.byteArrayToHexString(key));
//					resource.setIv(Helper.byteArrayToHexString(iv));
//					resource.setRights(rights);
//
//					databaseLayer.getResourceDao().update(resource);
//
//					currentStatus = EncryptResult.RE_ENCRYPT;
//					logger.info(new StringBuilder().append("[ re-encrypt] ").append(fromFile.getPath()).toString());
//				}
//			}
//		}
//		catch (IOException e)
//		{
//			logger.error("", e);
//		}
//		catch (DaoException e)
//		{
//			logger.error("", e);
//		}
//		catch (SecurityException e)
//		{
//			logger.error("", e);
//		}
//		catch (GeneralSecurityException e)
//		{
//			logger.error("", e);
//		}
//		finally
//		{
//			try
//			{
//				if (encryptedContent != null)
//				{
//					encryptedContent.close();
//				}
//				if (toFileOuts != null)
//				{
//					toFileOuts.close();
//				}
//			}
//			catch (IOException e)
//			{
//				logger.error("", e);
//			}
//		}
		int currentStatus = EncryptResult.NEW_ENCRYPT;//测试用
		return currentStatus;
	}

	public EncryptResult encryptDir(File fromDir, boolean force, Informer informer)
	{
		if (!fromDir.exists())
		{
			logger.error(new StringBuilder().append(fromDir.getPath()).append(" not exists").toString());
			if (null != informer)
			{
				informer.informError(new StringBuilder().append(fromDir.getPath()).append(" 不存在").toString());
			}
			return null;
		}
		if (!fromDir.isDirectory())
		{
			logger.error(new StringBuilder().append(fromDir.getPath()).append(" is not a directory").toString());
			if (null != informer)
			{
				informer.informError(new StringBuilder().append(fromDir.getPath()).append(" 不是文件夹").toString());
			}
			return null;
		}
		if (!fromDir.canRead())
		{
			logger.error(new StringBuilder().append(fromDir.getPath()).append(" can not read").toString());
			if (null != informer)
			{
				informer.informError(new StringBuilder().append(fromDir.getPath()).append(" 不可读").toString());
			}
			return null;
		}

	
		logger.info(new StringBuilder().append("[batch encrypt request]\n")
				.append("  ------FROM: ").append(fromDir.getPath()).append("\n"));

		if (null != informer)
		{
			informer.informReady("正在计算需要上传文件个数");
		}

		EncryptResult result = new EncryptResult();
		result.NumberOfAll = recursivelyCount(fromDir);

		if (null != informer)
		{
			informer.informStart(result);
		}

		recursivelyEncrypt(fromDir,  result, force, informer);

		logger.info(new StringBuilder().append("[batch encrypt result] ")
				.append("all[").append(result.NumberOfAll).append("] ")
				.append("fail[").append(result.NumberOfFail).append("] ")
				.append("new-encrypt[").append(result.NumberOfNewEncrypt).append("] ")
				.append("old-encrypt[").append(result.NumberOfOldEncrypt).append("] ")
				.append("re-encrypt[").append(result.NumberOfReEncrypt).append("]").toString());

		if (null != informer)
		{
			informer.informEnd(result);
		}

		return result;
	}

	private int recursivelyCount(File fromDir)
	{
		int count = 0;
		File[] files = fromDir.listFiles();
		for (File file : files)
		{
			if (file.isDirectory())
			{
				count += recursivelyCount(file);
			}
			else if (file.isFile() && (file.getName().endsWith(sourceSuffix.toLowerCase()) || file.getName().endsWith(sourceSuffix.toUpperCase())))
			{
				count ++;
			}
		}
		return count;
	}

	private void recursivelyEncrypt(File fromDir,  EncryptResult result, boolean force, Informer informer)
	{
		result.FailedFiles=new Vector();
		File[] fromFiles = fromDir.listFiles();

		for (File fromFile : fromFiles)
		{
			if (fromFile.isDirectory())
			{
				
				recursivelyEncrypt(fromFile, result, force, informer);
			}
			else if (fromFile.isFile() && (fromFile.getName().endsWith(sourceSuffix.toLowerCase()) || fromFile.getName().endsWith(sourceSuffix.toUpperCase())))
			{
				int status = encryptFile(fromFile,  force);
				result.CurrentStatus = status;
				result.CurrentFilename = fromFile.getPath();
				switch (status)
				{
					case EncryptResult.FAIL: 
						result.NumberOfFail++; 
						result.FailedFiles.addElement(fromFile.getName());
						break;
					case EncryptResult.NEW_ENCRYPT: 
						result.NumberOfNewEncrypt++; 
						break;
					case EncryptResult.OLD_ENCRYPT: 
						result.NumberOfOldEncrypt++; 
						break;
					case EncryptResult.RE_ENCRYPT: 
						result.NumberOfReEncrypt++; 
						break;
				}
				if (null != informer)
				{
					informer.informProgress(result);
				}
			}
		}
	}
}


