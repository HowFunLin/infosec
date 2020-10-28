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
	 * ���ֽ��������Ϊ16���ƴ�
	 * @param bs ����ת�����ֽ�����
	 * @return ת���õ���16�����ַ���
	 */
	public static String byteArrayToHex(byte[] bs)
	{
		StringBuilder res = new StringBuilder();
		
		for(byte b: bs) res.append(String.format("%02X", (b & 0xff)));
			
		return res.toString();
	}
	
	/**
	 * �����㷨
	 */
	public static void encrypt()
	{
		//DES�����㷨
		Cipher des = null;

		//��Կ
		byte[] des_key = new byte[8];

		//����
		byte[] des_input = new byte[8];

		//���ܺ�����
		byte[] des_output = null;

		for (int i = 0; i < 8; i++)
		{
			des_key[i] = 0x11;
			des_input[i] = 0x11;
		}
		
		BitsArray bs = new BitsArray(des_key);
		System.out.println(bs.toString());

		//����DES��Կ
		SecretKey secretKey = new SecretKeySpec(des_key, "DES");

		//����DES�����㷨����ָ�����뱾ģʽ������䷽ʽ
		try
		{
			des = Cipher.getInstance("DES/ECB/NoPadding");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e)
		{
			e.printStackTrace();
		}

		//��ʼ��DES�㷨
		try
		{
			des.init(Cipher.ENCRYPT_MODE, secretKey);
		} catch (InvalidKeyException e)
		{
			e.printStackTrace();
		}

		//����
		try
		{
			des_output = des.doFinal(des_input);
		} catch (IllegalBlockSizeException | BadPaddingException e)
		{
			e.printStackTrace();
		}

		// ������ܽ��
		System.out.print(byteArrayToHex(des_output));
	}
}

class BitsArray
{
	private String str;
	
	//����һ��ָ�����ȵ�λ������ʼ��λֵΪ0
	BitsArray(int length)
	{
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < length; i++) sb.append("0");
		str = sb.toString();
	}
	
	//���ַ����鹹��λ��
	BitsArray(byte[] bs)
	{
		fromByteArray(bs);
	}
	
	//����λ���ĳ���
	int length()
	{
		return str.length();
	}
	
	//��ɴ��ַ����鵽λ����ת��
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
	
	//ת��λ�ַ�����
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