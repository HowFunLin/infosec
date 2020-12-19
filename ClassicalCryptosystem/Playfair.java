package ClassicalCryptosystem;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Scanner;

/**
  * ���������㷨��Playfair�����㷨
  * ������ʾ����ؼ��ֺ����ģ������Ӧ������
 * @author 3118004684�ֺƷ�
 */
public class Playfair
{
	private char[][] matrix = new char[5][5];
	private static String keyword;
	private Scanner scan;
	
	{
		System.out.print("Please enter the keyword of Playfair: ");
		
		//������Ĺؼ���ת��Ϊ�ַ����飬��������Ĺؼ��ֽ����пպͷ�Χ�ж�
		scan = new Scanner(System.in);
		keyword = scan.nextLine();
		if(keyword.isBlank())
		{
			System.out.println("Please enter the keyword!");
			System.exit(0);
		}
		char[] kc = keyword.toUpperCase().toCharArray();
		for(int i = 0; i < kc.length; i++)
		{
			if(kc[i] > 90 || kc[i] < 65)
			{
				System.out.println("Please enter the correct words!");
				System.exit(0);
			}
		}
		
		//��ȡ��ĸ����д��ĸ����������ĸJ����������������ʱ��I�ϲ�
		char[] alphabet = new char[26];
		for(char c = 'A', i = 0; i < 25; c++, i++) 
		{
			alphabet[i] = c;
			if(alphabet[i] == 'I') c++;
		}
		
		//����Set�����Ի���ַ�������ͬ���ַ�����
		LinkedHashSet<Character> set = new LinkedHashSet<Character>();
		for(int i = 0; i < kc.length; i++) set.add(kc[i]);
		for(int i = 0; i < 25; i++)
		{
			set.add(alphabet[i]);
			if(set.size() == 25) break;
		}
		
		//���ַ������е�Ԫ�ذ����к��е�˳��������
		Character[] array = set.toArray(new Character[set.size()]);
		int k = 0;
		for(int i = 0; i < matrix.length; i++)
		{
			for(int j = 0; j < matrix[i].length; j++)
			{
				matrix[i][j] = array[k];
				k++;
			}
		}
		
		//�����������
		System.out.println("The matrix is: ");
		for(int i = 0; i < matrix.length; i++)
		{
			for(int j = 0; j < matrix[i].length; j++)
			{
				System.out.print(matrix[i][j] + " ");
			}
			
			System.out.println();
		}
	}
	
	/**
	  * �ھ�����Ѱ��Ŀ����ĸ��λ�ã������к��е�����
	 * @param c ��ҪѰ�ҵ���ĸ
	 * @return ����Ϊ2�������ʾ���к���
	 */
	private int[] search(char c)
	{
		int[] in = {5, 5};
		for(int i = 0; i < matrix.length; i++)
		{
			for(int j = 0; j < matrix[i].length; j++)
			{
				if(matrix[i][j] == c)
				{
					in[0] = i;
					in[1] = j;
				}
			}
		}
		
		//���ޱ仯��˵�����޴���ĸ�������ĸΪJ��������I��J�ϲ�����Ϊ����I��λ��
		if(in[0] == 5 && in[1] == 5) 
		{
			c -= 1;
			return search(c);
		}
		
		return in;
	}
	
	/**
	 * �����㷨
	 */
	public void encrypt()
	{
		//��ȡ���ģ��������еĿո�ɾȥ����ȫ��ת��Ϊ��д��ĸ�ַ�����
		System.out.print("Please enter the plaintext: ");
		Scanner scan = new Scanner(System.in);
		String plaintext = scan.nextLine();		
		char[] plaintextCharArray = plaintext.replaceAll(" ", "").toUpperCase().toCharArray();
		scan.close();
		
		//���ַ�����������������ӽ�ArrayList��
		ArrayList<Character> list = new ArrayList<Character>();
		for(int i = 0; i < plaintextCharArray.length; i++) list.add(plaintextCharArray[i]);
		
		//�����ķ�������ظ���ĸ��һ�飬�����ظ���������ĸ�в���һ�������ĸk���зָ������·���
		for(int i = 0; i < plaintextCharArray.length; i = i + 2) 
		{
			if(i + 1 == plaintextCharArray.length) break; //��ֹ�������鷶Χ
			if(list.get(i) == list.get(i + 1)) list.add(i + 1, 'K');
		}
		
		//�����鵽���һ��ʱֻ��һ����ĸ���򲹳���ĸk
		if(list.size() % 2 != 0) list.add('K');
		
		//�������
		System.out.print("The cipher text is: ");
		for(int i = 0; i < list.size(); i = i + 2)
		{
			char a = list.get(i);
			char b = list.get(i + 1);
			
			//��ȡ������ĸ�ھ����е�����
			int[] posA = search(a);
			int[] posB = search(b);
			
			int lineA = posA[0];
			int lineB = posB[0];
			int rowA = posA[1];
			int rowB = posB[1];
			
			//��������ĸ�ھ�����ͬ�У���ѭ��ȡ���ұ���ĸΪ����
			if(lineA == lineB) System.out.print(matrix[lineA][(rowA + 1) % 5] + "" + matrix[lineB][(rowB + 1) % 5] + " ");
			
			//��������ĸ�ھ�����ͬ�У���ѭ��ȡ���±���ĸΪ����
			else if(rowA == rowB) System.out.print(matrix[(lineA + 1) % 5][rowA] + "" + matrix[(lineB + 1) % 5][rowB] + " ");
			
			//��������ĸ�ھ����в�ͬ�в�ͬ�У���ȡ��ͬ��������һ��ĸͬ�е���ĸΪ����
			else System.out.print(matrix[lineA][rowB] + "" + matrix[lineB][rowA] + " ");
		}
	}
	
	public static void main(String[] args)
	{
		//monarchy
		//we are discovered save yourself
		Playfair pf = new Playfair();
		pf.encrypt();
	}
}