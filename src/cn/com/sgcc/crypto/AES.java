package cn.com.sgcc.crypto;

import java.util.Properties;

import org.apache.commons.codec.binary.Base64;  
import org.apache.commons.codec.digest.DigestUtils;  

import sun.reflect.generics.tree.ReturnType;

public class AES {

	static 
    { 
		// check jre bits
		Properties props = System.getProperties();
		String bits=String.valueOf(props.get("sun.arch.data.model"));
		// user dir D:/Java/SgccUI
		String userdir = System.getProperty("user.dir"); 
		System.out.println(userdir);
//		System.load(userdir + '\\' + "Sgcc" + bits + ".dll");
		//System.load("D:/Java/SgccUI/src/sgccDLL.dll");
		//System.out.println(System.getProperty("java.library.path"));
    }
	
	
	public native static String encryptApi(String s);
	public native static String decryptApi(String s); 
	
	public static String encrypt(String s){
		return base64Encode(s); 
//		return encryptApi(s);
	}
	
	public static String decrypt(String s){
		String str=new String(base64Decode(s));
		return str;
//		return decryptApi(s);
	}
	
	
	 public static String base64Encode(String data){  
         
	        return Base64.encodeBase64String(data.getBytes());  
	    }  
	      
	    public static byte[] base64Decode(String data){  
	          
	        return Base64.decodeBase64(data.getBytes());  
	    }  
	      
	    public static String md5(String data) {  
	          
	        return DigestUtils.md5Hex(data);  
	    }  
	      
	    public static String sha1(String data) {  
	          
	        return DigestUtils.shaHex(data);  
	    }  
	      
	    public static String sha256Hex(String data) {  
	          
	        return DigestUtils.sha256Hex(data);  
	    }  
	      
	
//	public static void main(String[] args) {
//		String base64 = base64Encode("Today is a good day!");  
//        System.out.println("base64 encode="+base64);  
//          
//        byte[] buf = base64Decode(base64);  
//        System.out.println("base64 decode="+new String(buf));  
//          
//        String md5 = md5("Today is a good day!");  
//        System.out.println("md5="+md5+"**len="+md5.length());  
//          
//        String sha1 = sha1("Today is a good day!");  
//        System.out.println("sha1="+sha1+"**len="+sha1.length());  
//		
//        
//		System.out.println(AES.decrypt(encrypt("Q_GDW_102-2003|35108342C3EEB8B540535808C1E3FC84|8A5E12542865DF8BF23166DA794C5094|null|true|false|false|false|false|false|-1|-1")));
////		System.out.println(AES.encrypt("1|CC495C74019886FB21B68E4DAE5373C5|49BDF7DB70F64AD0150AE5AF95E3B3E1|null|true|false|false|false|false|false|-1|-1"));
//	//	for(int i=0;i<100;i++){
////			//System.out.println(AES.encrypt("1|CC495C74019886FB21B68E4DAE5373C5|49BDF7DB70F64AD0150AE5AF95E3B3E1|null|true|false|false|false|false|false|-1|-1"));
////			System.out.println(AES.encrypt("1|CC495C74019886FBIKSIEKWQKDUJ73C5|49BDF7DB70F64AD0150AE5AF95E3B3E1|null|true|false|false|false|false|false|-1|-1"));
////		}
////System.out.println(decryptApi("FFA203DE5B184AE1F8ECCD4B667ED69D3440E698C697B42CBB3E8D92F5D277756464"));
//		//System.out.println(decryptApi("85C9472633E7CB"));
//		//System.out.println(decryptApi(encryptApi("Q_GDW_102-2003|35108342C3EEB8B540535808C1E3FC84|8A5E12542865DF8BF23166DA794C5094|null|true|false|false|false|false|false|-1|-1")));
//		//System.out.println(encryptApi("3E1|null|true|false|false|false|"));
//	}

}
