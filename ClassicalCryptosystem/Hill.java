package ClassicalCryptosystem;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * m=3的Hill密码加密算法 
  * 根据提示输入矩阵中各个位置的数值，并输入所需加密的明文
 * @author 3118004684林浩帆
 */
public class Hill
{
	private int m = 3;
	
	/**
	 * 加密算法
	 */
	public void encrypt()
	{
		int[][] k = new int[m][m];
		
		//自定义加密密钥矩阵
		System.out.println("Please enter the number of key matrix k: ");
		Scanner scan1 = new Scanner(System.in);
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				System.out.print("Please enter k[" + i + "][" + j + "]: ");
				k[i][j] = scan1.nextInt();
			}
		}
		
		//获取明文，将明文中的空格删去，并全部转换为大写字母字符数
		System.out.print("Please enter the plaintext: ");
		Scanner scan2 = new Scanner(System.in);
		String plaintext = scan2.nextLine();		
		char[] plaintextCharArray = plaintext.replaceAll(" ", "").toUpperCase().toCharArray();
		
		//将字符数组中所有内容添加进ArrayList中
		ArrayList<Character> list = new ArrayList<Character>();
		for(int i = 0; i < plaintextCharArray.length; i++) list.add(plaintextCharArray[i]);
		
		//明文不足按3个字母一组分组时补字母x
		while(list.size() % 3 != 0) list.add('x');
		
		//输出密文
		System.out.print("The ciphertext is: ");
		for(int i = 0; i < list.size(); i = i + 3)
		{
			char[] p = new char[3];
			
			p[0] = list.get(i);
			p[1] = list.get(i + 1);
			p[2] = list.get(i + 2);
			
			char[] c = new char[3];
			
			//线性方程，计算得出密文
			c[0] = (char) ((k[0][0] * (p[0] - 65) + k[0][1] * (p[1] - 65) + k[0][2] * (p[2] - 65)) % 26 + 65);
			c[1] = (char) ((k[1][0] * (p[0] - 65) + k[1][1] * (p[1] - 65) + k[1][2] * (p[2] - 65)) % 26 + 65);
			c[2] = (char) ((k[2][0] * (p[0] - 65) + k[2][1] * (p[1] - 65) + k[2][2] * (p[2] - 65)) % 26 + 65);

			System.out.print("" + c[0] + c[1] + c[2] + " ");
		}
		
		scan1.close();
		scan2.close();
	}
	
	public static void main(String[] args)
	{
		/*
		 	{17 17 5}
		k =	{21 18 21}
			{2 2 19}
		 
		pay more money
		*/
		Hill h = new Hill();
		h.encrypt();
	}
}