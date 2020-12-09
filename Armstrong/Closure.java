import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * ��ΪU���Ӽ������Լ�X����U�ϵĺ���������F�ıհ��㷨
 * @author Riyad
 */
public class Closure
{
	private Scanner scan = new Scanner(System.in);
	
	ArrayList<String> left = new ArrayList<String>();
	ArrayList<String> right = new ArrayList<String>();
	
	/**
	 * �������Լ�X
	 * @return ���Լ�
	 */
	private String inputX()
	{
		System.out.print("������Ҫ��հ������Լ�X�����磺AB����");
		String temp = scan.nextLine();
		String x = temp.replaceAll(" ", "");
		
		return x;
	}
	
	/**
	 * �������Լ�����U
	 * @param str �����U
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
	 * �����õıհ�
	 * @param x ���Լ�X
	 * @param u ���Լ�����U
	 * @return ����õıհ�
	 */
	private String outputClosure(String x, String u)
	{
		String lastOne = null;
		
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
		Closure c = new Closure();
		
		String x = c.inputX();
		String u = c.inputU();
		
		c.inputF();
		
		String closure = c.outputClosure(x, u);
		
		System.out.println("�õ��հ�Ϊ��" + closure);
		
	}
}