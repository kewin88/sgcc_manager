package cn.com.sgcc.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import sun.misc.BASE64Decoder;     
import sun.misc.BASE64Encoder;     

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import cn.com.sgcc.vo.*;
import cn.com.sgcc.crypto.AES;
import cn.com.sgcc.ds.*;

public class ResourceDaoImpl implements ResourceDao
{
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}
	public ResourceDaoImpl(){
		dataSource = new DataSourceImpl();
	}
	
	
	public  Resource decodeResourceHash(int id, String nameHash, String allHash) throws IOException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException{
// 	hash 按照以下顺序加密，以竖线分隔
//		Name
//		Key
//		Iv
//		BatchUuid
//		OnlineDisplayable
//		OnlineCopyable
//		OnlinePrintable
//		OfflineDisplayable
//		OfflineCopyable
//		OfflinePrintable
//		OfflineDisplayDuration
//		OfflineDisplayCount
		
//		BASE64Decoder decoder = new BASE64Decoder();
//		byte[] allHashBytes = decoder.decodeBuffer(allHash);
//		byte[] key = new byte[128/8];
//		byte[] iv = new byte[128/8];
//		SecureRandom sr = new SecureRandom("zhaoyue".getBytes());
//		sr.nextBytes(key);
//		sr.nextBytes(iv);
//		SecretKeySpec skSpec = new SecretKeySpec(key, "AES");
//		IvParameterSpec ivSpec = new IvParameterSpec(iv);
//		javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES" + "/" + "CBC" + "/" + "ISO10126Padding");
//		cipher.init(Cipher.DECRYPT_MODE, skSpec, ivSpec);
//		byte[] encryptedContent = cipher.doFinal(allHashBytes);
//		allHash = new String(encryptedContent);
		allHash = AES.decrypt(allHash);
		System.out.println(allHash);
		String[] s = allHash.split("\\|");
		int i = 0;
		Resource rt = new Resource();
		rt.setId(id);
		rt.setNameHash(nameHash);
		rt.setName(s[i++]);
		rt.setKey(s[i++]);
		rt.setIv(s[i++]);
		rt.setBatchUuid(s[i++]);
		rt.setRights(new Rights());
		rt.getRights().setOnlineDisplayable(Boolean.parseBoolean(s[i++]));
		rt.getRights().setOnlineCopyable(Boolean.parseBoolean(s[i++]));
		rt.getRights().setOnlinePrintable(Boolean.parseBoolean(s[i++]));
		rt.getRights().setOfflineDisplayable(Boolean.parseBoolean(s[i++]));
		rt.getRights().setOfflineCopyable(Boolean.parseBoolean(s[i++]));
		rt.getRights().setOfflinePrintable(Boolean.parseBoolean(s[i++]));
		rt.getRights().setOfflineDisplayDuration(Integer.parseInt(s[i++]));
		rt.getRights().setOfflineDisplayCount(Integer.parseInt(s[i++]));
		return rt;
	}
	
	public  String encodeResourceHash(Resource info) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
//	 	hash 按照以下顺序加密，以竖线分隔
//			Name
//			Key
//			Iv
//			BatchUuid
//			OnlineDisplayable
//			OnlineCopyable
//			OnlinePrintable
//			OfflineDisplayable
//			OfflineCopyable
//			OfflinePrintable
//			OfflineDisplayDuration
//			OfflineDisplayCount
			StringBuilder sb = new StringBuilder();//只用于方法中，比较安全
			sb.append(info.getName());sb.append("|");
			sb.append(info.getKey());sb.append("|");
			sb.append(info.getIv());sb.append("|");
			sb.append(info.getBatchUuid());sb.append("|");
			sb.append(Boolean.toString(info.getRights().getOnlineDisplayable()));sb.append("|");
			sb.append(Boolean.toString(info.getRights().getOnlineCopyable()));sb.append("|");
			sb.append(Boolean.toString(info.getRights().getOnlinePrintable()));sb.append("|");
			sb.append(Boolean.toString(info.getRights().getOfflineDisplayable()));sb.append("|");
			sb.append(Boolean.toString(info.getRights().getOfflineCopyable()));sb.append("|");
			sb.append(Boolean.toString(info.getRights().getOfflinePrintable()));sb.append("|");
			sb.append(Integer.toString(info.getRights().getOfflineDisplayDuration()));sb.append("|");
			sb.append(Integer.toString(info.getRights().getOfflineDisplayCount()));
			
//			byte[] key = new byte[128/8];
//			byte[] iv = new byte[128/8];
//			SecureRandom sr = new SecureRandom("zhaoyue".getBytes());
//			sr.nextBytes(key);
//			sr.nextBytes(iv);
//			SecretKeySpec skSpec = new SecretKeySpec(key, "AES");
//			IvParameterSpec ivSpec = new IvParameterSpec(iv);
//			javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES" + "/" + "CBC" + "/" + "ISO10126Padding");
//			cipher.init(Cipher.ENCRYPT_MODE, skSpec, ivSpec);
//			byte[] encryptedContent = cipher.doFinal(sb.toString().getBytes());
//			BASE64Encoder encoder = new BASE64Encoder();
//			return encoder.encode(encryptedContent);
			System.out.println(sb.toString());
			return AES.encrypt(sb.toString());
		}
	
	
	public static void main(String[] args) {
		ResourceDaoImpl r = new ResourceDaoImpl();
		try {
			r.decodeResourceHash(0, null, "fadsf||ff|ff");
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// may return null
	@Override
	public Resource select(int id) throws DaoException
	{
		Resource rt = null;
		Connection conn = null;
		try
		{
			String sql = "select id,nameHash,allHash where id=?";
			conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
			{
				rt = new Resource();
				rt = decodeResourceHash(rs.getInt(1),rs.getString(2),rs.getString(3));
			}
			rs.close();
			stmt.close();
		} 
		catch (SQLException e)
		{
			throw new DaoException(e);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally
		{
			if (null != conn)
			{
				try
				{
					conn.close();
				}
				catch (SQLException e)
				{
					throw new DaoException(e);
				}
			}
		}
		return rt;
	}

	// may return null
	@Override
	public Resource select(String nameHash) throws DaoException
	{
		Resource rt = null;
		Connection conn = null;
		try
		{
			String sql = "select id,nameHash,allHash from resource where nameHash=?";
			conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, nameHash);
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
			{
				rt = new Resource();
				rt = decodeResourceHash(rs.getInt(1),rs.getString(2),rs.getString(3));
			}
			rs.close();
			stmt.close();
		} 
		catch (SQLException e)
		{
			throw new DaoException(e);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally
		{
			if (null != conn)
			{
				try
				{
					conn.close();
				}
				catch (SQLException e)
				{
					throw new DaoException(e);
				}
			}
		}
		return rt;
	}

	// the size of list can be 0
	@Override
	public List<Resource> selectAll() throws DaoException
	{
		List<Resource> rt = new ArrayList<Resource>();
		Connection conn = null;
		try
		{
			String sql = "select id,nameHash,allHash from resource";
			conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next())
			{
				Resource info = new Resource();
				info = decodeResourceHash(rs.getInt(1),rs.getString(2),rs.getString(3));
				rt.add(info);
			}
			rs.close();
			stmt.close();
		} 
		catch (SQLException e)
		{
			throw new DaoException(e);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally
		{
			if (null != conn)
			{
				try
				{
					conn.close();
				}
				catch (SQLException e)
				{
					throw new DaoException(e);
				}
			}
		}
		return rt;
	}

	// ignore id; if name conflict, throw DaoException
	@Override
	public void insert(Resource info) throws DaoException
	{
		Connection conn = null;
		try
		{
			String sql = "insert into resource(nameHash,allHash) values(?,?)";
			conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, info.getNameHash());
			stmt.setString(2, encodeResourceHash(info));
			stmt.execute();
			stmt.close();
		} 
		catch (SQLException e)
		{
			throw new DaoException(e);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally
		{
			if (null != conn)
			{
				try
				{
					conn.close();
				}
				catch (SQLException e)
				{
					throw new DaoException(e);
				}
			}
		}
	}

	// no such id or name conflict, throw DaoException
	@Override
	public void update(Resource info) throws DaoException
	{
		Connection conn = null;
		try
		{
			String sql = "update resource set nameHash=?,allHash=? where id=?";
			conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, info.getNameHash());
			stmt.setString(2, encodeResourceHash(info));
			stmt.execute();
			stmt.close();
		} 
		catch (SQLException e)
		{
			throw new DaoException(e);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally
		{
			if (null != conn)
			{
				try
				{
					conn.close();
				}
				catch (SQLException e)
				{
					throw new DaoException(e);
				}
			}
		}
	}

	// no such id, throw DaoException
	@Override
	public void delete(int id) throws DaoException
	{
		Connection conn = null;
		try
		{
			String sql = "delete from resource where id=?";
			conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.execute();
			stmt.close();
		} 
		catch (SQLException e)
		{
			throw new DaoException(e);
		} 
		finally
		{
			if (null != conn)
			{
				try
				{
					conn.close();
				}
				catch (SQLException e)
				{
					throw new DaoException(e);
				}
			}
		}
	}

}
