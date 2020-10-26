package ClassicalCryptosystem;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * m=3��Hill��������㷨 
  * ������ʾ��������и���λ�õ���ֵ��������������ܵ�����
 * @author 3118004684�ֺƷ�
 */
public class Hill
{
	private int m = 3;
	
	/**
	 * �����㷨
	 */
	public void encrypt()
	{
		int[][] k = new int[m][m];
		
		//�Զ��������Կ����
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
		
		//��ȡ���ģ��������еĿո�ɾȥ����ȫ��ת��Ϊ��д��ĸ�ַ���
		System.out.print("Please enter the plaintext: ");
		Scanner scan2 = new Scanner(System.in);
		String plaintext = scan2.nextLine();		
		char[] plaintextCharArray = plaintext.replaceAll(" ", "").toUpperCase().toCharArray();
		
		//���ַ�����������������ӽ�ArrayList��
		ArrayList<Character> list = new ArrayList<Character>();
		for(int i = 0; i < plaintextCharArray.length; i++) list.add(plaintextCharArray[i]);
		
		//���Ĳ��㰴3����ĸһ�����ʱ����ĸx
		while(list.size() % 3 != 0) list.add('x');
		
		//�������
		System.out.print("The ciphertext is: ");
		for(int i = 0; i < list.size(); i = i + 3)
		{
			char[] p = new char[3];
			
			p[0] = list.get(i);
			p[1] = list.get(i + 1);
			p[2] = list.get(i + 2);
			
			char[] c = new char[3];
			
			//���Է��̣�����ó�����
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