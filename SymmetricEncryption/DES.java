package SymmetricEncryption;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DES
{
	public static void main(String[] args)
	{
		encrypt();
	}
	
	/**
	 * 将字节数组输出为16进制串
	 * @param bs 所需转换的字节数组
	 * @return 转换得到的16进制字符串
	 */
	public static String byteArrayToHex(byte[] bs)
	{
		StringBuilder res = new StringBuilder();
		
		for(byte b: bs) res.append(String.format("%02X", (b & 0xff)));
			
		return res.toString();
	}
	
	/**
	 * 加密算法
	 */
	public static void encrypt()
	{
		//DES加密算法
		Cipher des = null;

		//密钥
		byte[] des_key = new byte[8];

		//明文
		byte[] des_input = new byte[8];

		//加密后的输出
		byte[] des_output = null;

		for (int i = 0; i < 8; i++)
		{
			des_key[i] = 0x11;
			des_input[i] = 0x11;
		}
		
		BitsArray bs = new BitsArray(des_key);
		System.out.println(bs.toString());

		//创建DES密钥
		SecretKey secretKey = new SecretKeySpec(des_key, "DES");

		//创建DES密码算法对象，指定电码本模式和无填充方式
		try
		{
			des = Cipher.getInstance("DES/ECB/NoPadding");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e)
		{
			e.printStackTrace();
		}

		//初始化DES算法
		try
		{
			des.init(Cipher.ENCRYPT_MODE, secretKey);
		} catch (InvalidKeyException e)
		{
			e.printStackTrace();
		}

		//加密
		try
		{
			des_output = des.doFinal(des_input);
		} catch (IllegalBlockSizeException | BadPaddingException e)
		{
			e.printStackTrace();
		}

		// 输出加密结果
		System.out.print(byteArrayToHex(des_output));
	}
}

class BitsArray
{
	private String str;
	
	//构造一个指定长度的位串，初始化位值为0
	BitsArray(int length)
	{
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < length; i++) sb.append("0");
		str = sb.toString();
	}
	
	//从字符数组构造位串
	BitsArray(byte[] bs)
	{
		fromByteArray(bs);
	}
	
	//返回位串的长度
	int length()
	{
		return str.length();
	}
	
	//完成从字符数组到位串的转换
	void fromByteArray(byte[] bs)
	{
		StringBuffer result = new StringBuffer();
	    for (int i = 0; i < bs.length; i++) 
	    {
	    	int length = Long.toString(bs[i] & 0xff, 2).length();
	    	String str = "";
	    	if(length < 8)
	    	{
	    		StringBuffer sb = new StringBuffer();
	    		for(int j = 0; j < 8 - length; j++) sb.append("0");
	    		str = sb.toString();
	    	}
	        result.append(str + Long.toString(bs[i] & 0xff, 2));
	    }
	    str =  result.toString();
	}
	
	//转换位字符数组
	byte[] ToByteArray()
	{
		String[] temp = new String[8];
		for(int i = 0; i < 8; i++)
		{
			int pos = 0;
			temp[i] = str.substring(pos, pos + 8);
			pos += 8;
		}
		
	    byte[] b = new byte[8];
	    for (int i = 0; i < b.length; i++)
	    {
	        b[i] = Long.valueOf(temp[i], 2).byteValue();
	    }
	    
	    return b;
	}
	
	@Override
	public String toString()
	{
		return str.toString();
	}
}