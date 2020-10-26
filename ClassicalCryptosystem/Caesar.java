package ClassicalCryptosystem;

import java.util.Scanner;

/**
  * �Զ�����Կλ��������������ܺ�Ψ���Ĺ��������㷨��������Сд��ʾ�������ô�д��ʾ��
  * ����1����2ѡ������ִ�е��㷨��1Ϊ�����㷨��2Ϊ�����㷨��
  * ��Ϊ���ܲ������������0С��26��������Ϊ��Կ��λ����������������������Կ��Ч����ֹ���� 
  * �������Ļ�����ִ������Ľ��ܻ���ܲ�����
 * @author 3118004684�ֺƷ�
 */
public class Caesar
{
	private int key;
	private Scanner scan;
	
	/**
	 * �����㷨������Сд��ʾ�����ģ������д��ʾ������
	 * @apiNote ��������ַ���ת��Ϊ�ַ����飬����ת����ͬʱ����ASCII����ַ����мӷ���λ��������������
	 * 			�ڽ��мӷ���λ����ʱ���жϸ��ַ��Ƿ�Ϊ�������ţ����ǣ������������λ��
	 * 			���жϼӷ��������Ƿ񳬳�26��Сд��ĸ�ķ�Χ�������������˻ص�ASCII���е�ǰ26��λ�á�
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
	  * �����㷨��Ψ���Ĺ����������д��ʾ�����ģ����Сд��ʾ������
	 * @apiNote ��������ַ���ת��Ϊ�ַ����飬����ת����ͬʱ����ASCII����ַ����м�����λ��������������
	 * 			�ڽ��м�����λ����ʱ���жϸ��ַ��Ƿ�Ϊ�������ţ����ǣ������������λ��
	 * 			���жϼ����������Ƿ�С��26����д��ĸ�ķ�Χ����С�ڣ���ǰ����ASCII���еĺ�26��λ�á�
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