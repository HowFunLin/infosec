package SymmetricEncryption;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

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
		//��Կ
		byte[] des_key = new byte[8];

		//����
		byte[] des_input = new byte[8];
		
		System.out.println("The default plaintext is 1111111111111111");
		System.out.println("The default key is 1111111111111111");

		//Ĭ������Ϊ1111111111111111
		//Ĭ����ԿΪ1111111111111111
		for (int i = 0; i < 8; i++)
		{
			des_key[i] = 0x11;
			des_input[i] = 0x11;
		}
		
		//���ܺ������ΪF40379AB9E0EC533
		byte[] des_output = encrypt(des_key, des_input);
		
		System.out.println("------------------------------------------");
		
		BitsArray key = new BitsArray(des_key);
		BitsArray input = new BitsArray(des_input);
		BitsArray output = new BitsArray(des_output);
		
		//ѡ����ı�λ���������Ļ�����Կ��������������ֵʱ�Զ���ֹ����
		System.out.print("Please enter which one you want to change (1.Plaintext 2.Key): ");
		Scanner scan1 = new Scanner(System.in);
		int select = scan1.nextInt();
		if(select > 2 || select < 1)
		{
			System.out.println("Please enter the correct number!");
			System.exit(0);
		}
		
		//������ı��λ������������Χʱ�Զ���ֹ����
		System.out.print("Please enter how many bits you want to change (1 <= bits <= 64): ");
		Scanner scan2 = new Scanner(System.in);
		int bits = scan2.nextInt();
		if(bits > 64 || bits < 1)
		{
			System.out.println("Please enter correct number!");
			System.exit(0);
		}
		
		if(select == 1)
		{
			int count = 0; //��¼�ܸı��λ��������
			
			//�ܹ�����ʮ�β���
			for(int j = 0; j < 10; j++)
			{	
				//����Set��Ԫ�ز����ظ�����������õ�Ҫ�޸�λ���ڵ�λ��
				HashSet<Integer> hs = new HashSet<Integer>();
				for(int i = 0; i < bits; i++)
				{
					while(hs.size() == i) hs.add((int)(Math.random() * 64));
				}
				
				//��Ԫ�طŽ�ArrayList�Թ�����
				ArrayList<Integer> list = new ArrayList<Integer>();
				for(int i: hs) list.add(i);
				
				//��¡һ��ԭ���ĵı���
				BitsArray inputCopy = input.clone();
							
				//�����Ľ���ָ��λ�����޸�
				for(int i = 0; i < bits; i++)
				{
					int pos = list.get(i); //Ҫ�޸ĵ�λ��λ��
					
					if(input.toString().charAt(pos) == '0') input.setOne(pos);
					else if(input.toString().charAt(pos) == '1') input.setZero(pos);
				}
				
				//���޸ĺ���������Ϊbyte����
				des_input = input.toByteArray();
				
				//ʹ���޸ĺ�����ĺ�ԭ������Կ���м������㣬�õ��µ�����byte����
				byte[] des_newOutput = encrypt(des_key, des_input);
				
				//���µ�����byte����ת��Ϊλ������
				BitsArray newOutput = new BitsArray(des_newOutput);
				
				//��ԭ����������ĵ�λ������������
				newOutput.xor(output);
				
				//�������֮��λ����1�ĸ�������Ϊ�ı��λ��
				count += newOutput.OnesCount();
				
				//�����ѱ��޸ĵ�����Ϊԭ����
				input = inputCopy;
			}
			
			System.out.println("The average number of changed bits is " + ((double) count / 10));
		}
		else if(select == 2)
		{
			int count = 0; //��¼�ܸı��λ��������
			
			//�ܹ�����ʮ�β���
			for(int j = 0; j < 10; j++)
			{	
				//����Set��Ԫ�ز����ظ�����������õ�Ҫ�޸�λ���ڵ�λ��
				HashSet<Integer> hs = new HashSet<Integer>();
				for(int i = 0; i < bits; i++)
				{
					while(hs.size() == i) hs.add((int)(Math.random() * 64));
				}
				
				//��Ԫ�طŽ�ArrayList�Թ�����
				ArrayList<Integer> list = new ArrayList<Integer>();
				for(int i: hs) list.add(i);
				
				//��¡һ��ԭ��Կ�ı���
				BitsArray keyCopy = key.clone();
							
				//����Կ����ָ��λ�����޸�
				for(int i = 0; i < bits; i++)
				{
					int pos = list.get(i); //Ҫ�޸ĵ�λ��λ��
					
					if(key.toString().charAt(pos) == '0') key.setOne(pos);
					else if(key.toString().charAt(pos) == '1') key.setZero(pos);
				}
				
				//���޸ĺ����Կ���Ϊbyte����
				des_key = key.toByteArray();
				
				//ʹ���޸ĺ����Կ��ԭ�������Ľ��м������㣬�õ��µ�����byte����
				byte[] des_newOutput = encrypt(des_key, des_input);
				
				//���µ�����byte����ת��Ϊλ������
				BitsArray newOutput = new BitsArray(des_newOutput);
				
				//��ԭ����������ĵ�λ������������
				newOutput.xor(output);
				
				//�������֮��λ����1�ĸ�������Ϊ�ı��λ��
				count += newOutput.OnesCount();
				
				//�����ѱ��޸ĵ���ԿΪԭ��Կ
				key = keyCopy;
			}
			
			System.out.println("The average number of changed bits is " + ((double) count / 10));
		}
		
		scan1.close();
		scan2.close();
	}
	
	/**
	 * ���ֽ��������Ϊ16���ƴ�
	 * @param bs ����ת�����ֽ�����
	 * @return ת���õ���16�����ַ���
	 */
	/*
	public static String byteArrayToHex(byte[] bs)
	{
		StringBuilder res = new StringBuilder();
		
		for(byte b: bs) res.append(String.format("%02X", (b & 0xff)));
			
		return res.toString();
	}
	*/
	
	/**
	 * DES�����㷨
	 * @param des_key ��Կ
	 * @param des_input ��������
	 * @return �������
	 */
	public static byte[] encrypt(byte[] des_key, byte[] des_input)
	{
		//DES�����㷨
		Cipher des = null;

		//���ܺ�����
		byte[] des_output = null;
		
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
		//System.out.println("The cipher text is " + byteArrayToHex(des_output));
		
		return des_output;
	}
}

class BitsArray
{
	private String str;
	
	/**
	 * ����һ��ָ�����ȵ�λ������ʼ��λֵΪ0
	 * @param length λ���ĳ���
	 */
	BitsArray(int length)
	{
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < length; i++) sb.append("0");
		str = sb.toString();
	}
	
	/**
	 * ���ַ����鹹��λ��
	 * @param bs ���ڹ���λ����byte����
	 */
	BitsArray(byte[] bs)
	{
		fromByteArray(bs);
	}
	
	/**
	 * ����λ���ĳ���
	 * @return λ���ĳ���
	 */
	int length()
	{
		return str.length();
	}
	
	/**
	 * ��ɴ�byte���鵽λ����ת��
	 * @param bs Ҫת��Ϊλ����byte����
	 */
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
	
	/**
	 * ��λ������ת��Ϊbyte����
	 * @return λ�������Ӧ��byte����
	 */
	byte[] toByteArray()
	{
		String[] temp = new String[8];
		int pos = 0;
		for(int i = 0; i < 8; i++)
		{
			temp[i] = str.substring(pos, pos + 8);
			pos += 8;
		}
		
	    byte[] b = new byte[8];
		for (int i = 0; i < b.length; i++) b[i] = Long.valueOf(temp[i], 2).byteValue();
	    
	    return b;
	}
	
	/**
	 * ����һ��λ������������
	 * @param other �������λ��������������λ��
	 */
	void xor(BitsArray other)
	{
		String otherStr = other.toString();
		char[] cs = str.toCharArray();
		for(int i = 0; i < otherStr.length(); i++)
		{
			if(str.charAt(i) == otherStr.charAt(i)) cs[i] = '0';
			else cs[i] = '1';
		}
		str = Arrays.toString(cs).replaceAll("[\\[\\]\\s,]", "");
	}
	
	/**
	 * ����λ����1�ĸ���
	 * @return λ����1�ĸ���
	 */
	int OnesCount()
	{
		int count = 0;
		for(int i = 0; i < str.length(); i++) if(str.charAt(i) == '1') count++;
		
		return count;
	}
	
	/**
	 * ��¡һ������Ŀ���
	 */
	@Override
	protected BitsArray clone()
	{
		byte[] bs = toByteArray();
		BitsArray bitsArray = new BitsArray(bs);
		
		return bitsArray;
	}
	
	/**
	 * ��ָ������λ�õ�ֵ�趨Ϊ1
	 * @param index ָ��������
	 */
	void setOne(int index)
	{
		char[] cs = str.toCharArray();
		cs[index] = '1';
		str = Arrays.toString(cs).replaceAll("[\\[\\]\\s,]", "");
	}
	
	/**
	 * ��ָ������λ�õ�ֵ�趨Ϊ0
	 * @param index ָ��������
	 */
	void setZero(int index)
	{
		char[] cs = str.toCharArray();
		cs[index] = '0';
		str = Arrays.toString(cs).replaceAll("[\\[\\]\\s,]", "");
	}
	
	/**
	 * ����ָ������λ�õ�ֵ
	 * @param index ָ��������
	 * @param value �����趨��ֵ
	 */
	void set(int index, int value)
	{
		char[] cs = str.toCharArray();
		cs[index] = (char) ('0' + value);
		str = Arrays.toString(cs).replaceAll("[\\[\\]\\s,]", "");
	}
	
	/**
	 * ����λ�����ַ�����ʽ
	 */
	@Override
	public String toString()
	{
		return str.toString();
	}
}