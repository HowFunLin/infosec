package ClassicalCryptosystem;

import java.util.Scanner;

/**
  * 自定义密钥位数的恺撒密码加密和唯密文攻击解密算法，明文用小写表示，密文用大写表示。
  * 输入1或者2选择所需执行的算法，1为加密算法，2为解密算法。
  * 若为加密操作，输入大于0小于26的整数作为密钥的位数，若输入错误则会提醒密钥无效并终止程序。 
  * 输入密文或明文执行所需的解密或加密操作。
 * @author 3118004684林浩帆
 */
public class Caesar
{
	private int key;
	private Scanner scan;
	
	/**
	 * 加密算法，输入小写表示的明文，输出大写表示的密文
	 * @apiNote 将输入的字符串转化为字符数组，并在转化的同时利用ASCII表对字符进行加法移位操作并逐个输出。
	 * 			在进行加法移位操作时，判断该字符是否为其他符号，若是，则无需进行移位。
	 * 			并判断加法操作后是否超出26个小写字母的范围，若超出，则退回到ASCII表中的前26个位置。
	 */
	public void encrypt()
	{
		System.out.print("Please enter the key of Caesar Algorithm: ");
		scan = new Scanner(System.in);
		
		int temp = scan.nextInt();
		
		if(temp > 0 && temp < 26) key = temp;
		else
		{
			System.out.println("Invalid key!");
			System.exit(0);
		}
		
		System.out.print("Please enter the plaintext: ");
		Scanner scan = new Scanner(System.in);
		String pt = scan.nextLine();
		scan.close();
		
		System.out.print("The ciphertext is: ");
		char[] c = new char[pt.length()];
		for(int i = 0; i < pt.length(); i++)
		{
			c[i] = pt.charAt(i);
			if(c[i] >= 97 && c[i] <= 122) 
			{
				c[i] += key;
				if(c[i] > 122) c[i] -= 26;	
				c[i] -= 32;
			}
			System.out.print(c[i]);
		}
	}
	
	/**
	  * 解密算法，唯密文攻击，输入大写表示的密文，输出小写表示的明文
	 * @apiNote 将输入的字符串转化为字符数组，并在转化的同时利用ASCII表对字符进行减法移位操作并逐个输出。
	 * 			在进行减法移位操作时，判断该字符是否为其他符号，若是，则无需进行移位。
	 * 			并判断减法操作后是否小于26个大写字母的范围，若小于，则前进到ASCII表中的后26个位置。
	 */
	public void decrypt()
	{
		System.out.print("Please enter the ciphertext: ");
		Scanner scan = new Scanner(System.in);
		String pt = scan.nextLine();
		scan.close();
		
		System.out.println("The plaintext is: ");
		System.out.println("KEY");
		char[] c = new char[pt.length()];
		for(key = 1; key <=25; key++)
		{
			System.out.print(key + "\t");
			for(int i = 0; i < pt.length(); i++)
			{
				c[i] = pt.charAt(i);
				if(c[i] >= 65 && c[i] <= 90) 
				{
					c[i] -= key;
					if(c[i] < 65) c[i] += 26;
					c[i] += 32;
				}
				System.out.print(c[i]);
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args)
	{
		System.out.print("Please enter number 1 or 2 to choose which operation you want to do (1.Encrypt 2.Decrypt): ");
		Scanner s = new Scanner(System.in);
		int flag = s.nextInt();
		
		if(flag != 1 && flag != 2) 
		{
			System.out.println("Please enter the correct number!");
			System.exit(0);
		}
		
		//gaul is divided into three parts
		//JDXO LV GLYLGHG LQWR WKUHH SDUWV
		Caesar c = new Caesar();
		if(flag == 1) c.encrypt();
		else if(flag == 2) c.decrypt();
		
		s.close();
	}
}