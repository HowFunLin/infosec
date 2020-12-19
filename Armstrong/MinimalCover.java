package Armstrong;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * ��F���м�С�����������С������
 * @author Riyad
 */
public class MinimalCover
{
	private Scanner scan = new Scanner(System.in);
	
	ArrayList<String> left = new ArrayList<String>();
	ArrayList<String> right = new ArrayList<String>();
	
	/**
	 * �������Լ�����U
	 * @return ���Լ�������ַ���
	 */
	private String inputU()
	{
		System.out.print("���������Լ�U�����磺A, B, C, D, E����");
		String str = scan.nextLine();
		String temp = str.replaceAll(",", "");
		String u = temp.replaceAll(" ", "");
		
		return u;
	}
	
	/**
	 * ���뺯��������
	 */
	private void inputF()
	{
		System.out.print("������������ϵ�ĸ�����");
		int num = scan.nextInt();
		
		System.out.println("���뺯��������F��");
		for(int i = 1; i <= num; i++)
		{
			System.out.print("�������" + i + "������������ߵ�ֵ��");
			String valueLeft = scan.next();
			left.add(valueLeft);
			
			System.out.print("�������" + i + "�����������ұߵ�ֵ��");
			String valueRight = scan.next();
			right.add(valueRight);
		}
	}
	
	/**
	 * �����к��������ұߵ�ֵ���в��
	 */
	private void checkY()
	{
		for(int i = 0; i < left.size(); )
		{
			int length = right.get(i).length();
			
			if(length > 1)
			{
				for(int j = 0; j < length; j++)
				{
					left.add(left.get(i));
					right.add(String.valueOf(right.get(i).charAt(j)));
				}
				
				left.remove(i);
				right.remove(i);
				
				continue;
			}
			
			i++;
		}
	}
	
	/**
	 * ���F�и������������Է��������ĺ�����������ɾ��
	 * @param u ���Լ�����U
	 */
	private void check(String u)
	{
		ArrayList<String> oldLeft = new ArrayList<String>();
		ArrayList<String> oldRight = new ArrayList<String>();
		
		//��ԭ���ĺ����������б���
		for(int i = 0; i < left.size(); i++)
		{
			oldLeft.add(left.get(i));
			oldRight.add(right.get(i));
		}
		
		for(int i = 0; i < left.size(); )
		{
			String x = left.get(i);
			String a = right.get(i);
			
			left.remove(i);
			right.remove(i);
			
			String closure = outputClosure(x, u);
			
			if(closure.contains(a)) //���A����X�ıհ�����ԭ���ĺ�������Ҳ��ɾȥ��Ӧ��ֵ���Ա���ͬ��
			{
				oldLeft.remove(i);
				oldRight.remove(i);
				
				continue;
			}	
			else //������ԭΪԭ���ĺ�������
			{
				left.clear();
				right.clear();
				
				for(int j = 0; j < oldLeft.size(); j++)
				{
					left.add(oldLeft.get(j));
					right.add(oldRight.get(j));
				}
				
				i++;
			}
		}
	}
	
	/**
	 * ���F�и�����������ߵ�ֵ�����Է���������ֵ�����滻
	 * @param u ���Լ�����U
	 */
	private void checkX(String u)
	{
		for(int i = 0; i < left.size(); i++)
		{
			int length = left.get(i).length();
			
			if(length > 1)
			{
				String x = left.get(i);
				
				for(int j = 0; j < length; )
				{
					char b = x.charAt(j);
					String bj = String.valueOf(b);
					
					//ȥ��ԭ��X�е�Bj
					String newX = x.replace(bj, "");
					
					String closure = outputClosure(newX, u);
					
					if(closure.contains(right.get(i)))
					{
						x = newX;
						left.set(i, newX);
						continue;
					}
					
					j++;
				}
			}
		}
	}
	
	/**
	 * �����õıհ�
	 * @param x ���Լ�X
	 * @param u ���Լ�����U
	 * @return ����õıհ�
	 */
	private String outputClosure(String x, String u)
	{
		String lastOne = null;
		
		ArrayList<String> oldLeft = new ArrayList<String>();
		ArrayList<String> oldRight = new ArrayList<String>();
		
		//������հ�������ɾȥԭ�����������еĲ��ֺ������������ԭ���ĺ����������б���
		for(int i = 0; i < left.size(); i++)
		{
			oldLeft.add(left.get(i));
			oldRight.add(right.get(i));
		}
		
		while(true)
		{
			lastOne = x;
			
			for(int i = 0; i < left.size(); i++)
			{	
				if(x.contains(left.get(i)))
				{
					x += right.get(i);
					left.remove(i);
					right.remove(i);
				}
			}
			
			x = deleteRepeat(x);
			
			if(lastOne.equals(x) || x.equals(u)) 
				break;
		}
		
		//������ɺ�����ִ�ĺ����������ָ�ԭ��������
		left.clear();
		right.clear();
		
		for(int j = 0; j < oldLeft.size(); j++)
		{
			left.add(oldLeft.get(j));
			right.add(oldRight.get(j));
		}
		
		return x;
	}
	
	/**
	 * ɾ���ַ����е��ظ��ַ�
	 * @param str Ҫ���в������ַ���
	 * @return ɾȥ�ظ��ַ�����ַ���
	 */
	private String deleteRepeat(String str)
	{
		String deleted = "";
		
		HashSet<Character> noRepeat = new HashSet<Character>();
		
		for(int i = 0; i < str.length(); i++)
			noRepeat.add(str.charAt(i));
		
		Character[] ch = new Character[noRepeat.size()];
		Character[] array = noRepeat.toArray(ch);
		
		for(Character s : array)
			deleted += s;
		
		return deleted;
	}
	
	public static void main(String[] args)
	{
		MinimalCover mc = new MinimalCover();
		
		String u = mc.inputU();
		mc.inputF();
		
		mc.checkY();
		mc.check(u);
		mc.checkX(u);
		
		System.out.print("F����С������Fm = {");
		for(int i = 0; i < mc.left.size(); i++)
		{
			System.out.print(" " + mc.left.get(i) + "->" + mc.right.get(i) + " ");
		}
		System.out.println("}");
	}
}