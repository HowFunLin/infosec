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
		//密钥
		byte[] des_key = new byte[8];

		//明文
		byte[] des_input = new byte[8];
		
		System.out.println("The default plaintext is 1111111111111111");
		System.out.println("The default key is 1111111111111111");

		//默认明文为1111111111111111
		//默认密钥为1111111111111111
		for (int i = 0; i < 8; i++)
		{
			des_key[i] = 0x11;
			des_input[i] = 0x11;
		}
		
		//加密后的密文为F40379AB9E0EC533
		byte[] des_output = encrypt(des_key, des_input);
		
		System.out.println("------------------------------------------");
		
		BitsArray key = new BitsArray(des_key);
		BitsArray input = new BitsArray(des_input);
		BitsArray output = new BitsArray(des_output);
		
		//选择想改变位数的是明文或者密钥（当输入其他数值时自动终止程序）
		System.out.print("Please enter which one you want to change (1.Plaintext 2.Key): ");
		Scanner scan1 = new Scanner(System.in);
		int select = scan1.nextInt();
		if(select > 2 || select < 1)
		{
			System.out.println("Please enter the correct number!");
			System.exit(0);
		}
		
		//输入想改变的位数（当超出范围时自动终止程序）
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
			int count = 0; //记录总改变的位数的数量
			
			//总共进行十次测试
			for(int j = 0; j < 10; j++)
			{	
				//利用Set中元素不能重复的特性随机得到要修改位所在的位置
				HashSet<Integer> hs = new HashSet<Integer>();
				for(int i = 0; i < bits; i++)
				{
					while(hs.size() == i) hs.add((int)(Math.random() * 64));
				}
				
				//将元素放进ArrayList以供调用
				ArrayList<Integer> list = new ArrayList<Integer>();
				for(int i: hs) list.add(i);
				
				//克隆一份原明文的备份
				BitsArray inputCopy = input.clone();
							
				//对明文进行指定位数的修改
				for(int i = 0; i < bits; i++)
				{
					int pos = list.get(i); //要修改的位的位置
					
					if(input.toString().charAt(pos) == '0') input.setOne(pos);
					else if(input.toString().charAt(pos) == '1') input.setZero(pos);
				}
				
				//将修改后的明文输出为byte数组
				des_input = input.toByteArray();
				
				//使用修改后的明文和原来的密钥进行加密运算，得到新的密文byte数组
				byte[] des_newOutput = encrypt(des_key, des_input);
				
				//将新的密文byte数组转化为位串对象
				BitsArray newOutput = new BitsArray(des_newOutput);
				
				//与原来输出的密文的位串进行异或操作
				newOutput.xor(output);
				
				//计算异或之后位串中1的个数，即为改变的位数
				count += newOutput.OnesCount();
				
				//重置已被修改的明文为原明文
				input = inputCopy;
			}
			
			System.out.println("The average number of changed bits is " + ((double) count / 10));
		}
		else if(select == 2)
		{
			int count = 0; //记录总改变的位数的数量
			
			//总共进行十次测试
			for(int j = 0; j < 10; j++)
			{	
				//利用Set中元素不能重复的特性随机得到要修改位所在的位置
				HashSet<Integer> hs = new HashSet<Integer>();
				for(int i = 0; i < bits; i++)
				{
					while(hs.size() == i) hs.add((int)(Math.random() * 64));
				}
				
				//将元素放进ArrayList以供调用
				ArrayList<Integer> list = new ArrayList<Integer>();
				for(int i: hs) list.add(i);
				
				//克隆一份原密钥的备份
				BitsArray keyCopy = key.clone();
							
				//对密钥进行指定位数的修改
				for(int i = 0; i < bits; i++)
				{
					int pos = list.get(i); //要修改的位的位置
					
					if(key.toString().charAt(pos) == '0') key.setOne(pos);
					else if(key.toString().charAt(pos) == '1') key.setZero(pos);
				}
				
				//将修改后的密钥输出为byte数组
				des_key = key.toByteArray();
				
				//使用修改后的密钥和原来的明文进行加密运算，得到新的密文byte数组
				byte[] des_newOutput = encrypt(des_key, des_input);
				
				//将新的密文byte数组转化为位串对象
				BitsArray newOutput = new BitsArray(des_newOutput);
				
				//与原来输出的密文的位串进行异或操作
				newOutput.xor(output);
				
				//计算异或之后位串中1的个数，即为改变的位数
				count += newOutput.OnesCount();
				
				//重置已被修改的密钥为原密钥
				key = keyCopy;
			}
			
			System.out.println("The average number of changed bits is " + ((double) count / 10));
		}
		
		scan1.close();
		scan2.close();
	}
	
	/**
	 * 将字节数组输出为16进制串
	 * @param bs 所需转换的字节数组
	 * @return 转换得到的16进制字符串
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
	 * DES加密算法
	 * @param des_key 密钥
	 * @param des_input 明文输入
	 * @return 密文输出
	 */
	public static byte[] encrypt(byte[] des_key, byte[] des_input)
	{
		//DES加密算法
		Cipher des = null;

		//加密后的输出
		byte[] des_output = null;
		
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
		//System.out.println("The cipher text is " + byteArrayToHex(des_output));
		
		return des_output;
	}
}

class BitsArray
{
	private String str;
	
	/**
	 * 构造一个指定长度的位串，初始化位值为0
	 * @param length 位串的长度
	 */
	BitsArray(int length)
	{
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < length; i++) sb.append("0");
		str = sb.toString();
	}
	
	/**
	 * 从字符数组构造位串
	 * @param bs 用于构造位串的byte数组
	 */
	BitsArray(byte[] bs)
	{
		fromByteArray(bs);
	}
	
	/**
	 * 计算位串的长度
	 * @return 位串的长度
	 */
	int length()
	{
		return str.length();
	}
	
	/**
	 * 完成从byte数组到位串的转换
	 * @param bs 要转换为位串的byte数组
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
	 * 将位串对象转换为byte数组
	 * @return 位串对象对应的byte数组
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
	 * 与另一个位串进行异或操作
	 * @param other 用于与该位串进行异或操作的位串
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
	 * 计算位串中1的个数
	 * @return 位串中1的个数
	 */
	int OnesCount()
	{
		int count = 0;
		for(int i = 0; i < str.length(); i++) if(str.charAt(i) == '1') count++;
		
		return count;
	}
	
	/**
	 * 克隆一个自身的拷贝
	 */
	@Override
	protected BitsArray clone()
	{
		byte[] bs = toByteArray();
		BitsArray bitsArray = new BitsArray(bs);
		
		return bitsArray;
	}
	
	/**
	 * 将指定索引位置的值设定为1
	 * @param index 指定的索引
	 */
	void setOne(int index)
	{
		char[] cs = str.toCharArray();
		cs[index] = '1';
		str = Arrays.toString(cs).replaceAll("[\\[\\]\\s,]", "");
	}
	
	/**
	 * 将指定索引位置的值设定为0
	 * @param index 指定的索引
	 */
	void setZero(int index)
	{
		char[] cs = str.toCharArray();
		cs[index] = '0';
		str = Arrays.toString(cs).replaceAll("[\\[\\]\\s,]", "");
	}
	
	/**
	 * 设置指定索引位置的值
	 * @param index 指定的索引
	 * @param value 所需设定的值
	 */
	void set(int index, int value)
	{
		char[] cs = str.toCharArray();
		cs[index] = (char) ('0' + value);
		str = Arrays.toString(cs).replaceAll("[\\[\\]\\s,]", "");
	}
	
	/**
	 * 返回位串的字符串形式
	 */
	@Override
	public String toString()
	{
		return str.toString();
	}
}