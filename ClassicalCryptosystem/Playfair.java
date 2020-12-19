package ClassicalCryptosystem;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Scanner;

/**
  * 仅含加密算法的Playfair密码算法
  * 按照提示输入关键字和明文，输出相应的密文
 * @author 3118004684林浩帆
 */
public class Playfair
{
	private char[][] matrix = new char[5][5];
	private static String keyword;
	private Scanner scan;
	
	{
		System.out.print("Please enter the keyword of Playfair: ");
		
		//将输入的关键字转化为字符数组，并对输入的关键字进行判空和范围判断
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
		
		//获取字母表（大写字母），忽略字母J，后面在搜索矩阵时与I合并
		char[] alphabet = new char[26];
		for(char c = 'A', i = 0; i < 25; c++, i++) 
		{
			alphabet[i] = c;
			if(alphabet[i] == 'I') c++;
		}
		
		//利用Set的特性获得字符均不相同的字符数组
		LinkedHashSet<Character> set = new LinkedHashSet<Character>();
		for(int i = 0; i < kc.length; i++) set.add(kc[i]);
		for(int i = 0; i < 25; i++)
		{
			set.add(alphabet[i]);
			if(set.size() == 25) break;
		}
		
		//将字符数组中的元素按先行后列的顺序加入矩阵
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
		
		//输出矩阵内容
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
	  * 在矩阵中寻找目标字母的位置，返回行和列的数组
	 * @param c 所要寻找的字母
	 * @return 长度为2的数组表示的行和列
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
		
		//若无变化，说明查无此字母，则该字母为J，矩阵中I和J合并，改为搜索I的位置
		if(in[0] == 5 && in[1] == 5) 
		{
			c -= 1;
			return search(c);
		}
		
		return in;
	}
	
	/**
	 * 加密算法
	 */
	public void encrypt()
	{
		//获取明文，将明文中的空格删去，并全部转换为大写字母字符数组
		System.out.print("Please enter the plaintext: ");
		Scanner scan = new Scanner(System.in);
		String plaintext = scan.nextLine();		
		char[] plaintextCharArray = plaintext.replaceAll(" ", "").toUpperCase().toCharArray();
		scan.close();
		
		//将字符数组中所有内容添加进ArrayList中
		ArrayList<Character> list = new ArrayList<Character>();
		for(int i = 0; i < plaintextCharArray.length; i++) list.add(plaintextCharArray[i]);
		
		//若明文分组出现重复字母在一组，则在重复的明文字母中插入一个填充字母k进行分隔后重新分组
		for(int i = 0; i < plaintextCharArray.length; i = i + 2) 
		{
			if(i + 1 == plaintextCharArray.length) break; //防止超出数组范围
			if(list.get(i) == list.get(i + 1)) list.add(i + 1, 'K');
		}
		
		//若分组到最后一组时只有一个字母，则补充字母k
		if(list.size() % 2 != 0) list.add('K');
		
		//输出密文
		System.out.print("The cipher text is: ");
		for(int i = 0; i < list.size(); i = i + 2)
		{
			char a = list.get(i);
			char b = list.get(i + 1);
			
			//获取两个字母在矩阵中的坐标
			int[] posA = search(a);
			int[] posB = search(b);
			
			int lineA = posA[0];
			int lineB = posB[0];
			int rowA = posA[1];
			int rowB = posB[1];
			
			//若明文字母在矩阵中同行，则循环取其右边字母为密文
			if(lineA == lineB) System.out.print(matrix[lineA][(rowA + 1) % 5] + "" + matrix[lineB][(rowB + 1) % 5] + " ");
			
			//若明文字母在矩阵中同列，则循环取其下边字母为密文
			else if(rowA == rowB) System.out.print(matrix[(lineA + 1) % 5][rowA] + "" + matrix[(lineB + 1) % 5][rowB] + " ");
			
			//若明文字母在矩阵中不同行不同列，则取其同行且与下一字母同列的字母为密文
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