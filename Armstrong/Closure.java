import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * 求为U的子集的属性集X关于U上的函数依赖集F的闭包算法
 * @author Riyad
 */
public class Closure
{
	private Scanner scan = new Scanner(System.in);
	
	ArrayList<String> left = new ArrayList<String>();
	ArrayList<String> right = new ArrayList<String>();
	
	/**
	 * 输入属性集X
	 * @return 属性集
	 */
	private String inputX()
	{
		System.out.print("请输入要求闭包的属性集X（例如：AB）：");
		String temp = scan.nextLine();
		String x = temp.replaceAll(" ", "");
		
		return x;
	}
	
	/**
	 * 输入属性集总体U
	 * @param str 输入的U
	 * @return 属性集总体的字符串
	 */
	private String inputU()
	{
		System.out.print("请输入属性集U（例如：A, B, C, D, E）：");
		String str = scan.nextLine();
		String temp = str.replaceAll(",", "");
		String u = temp.replaceAll(" ", "");
		
		return u;
	}
	
	/**
	 * 输入函数依赖集
	 */
	private void inputF()
	{
		System.out.print("请输入依赖关系的个数：");
		int num = scan.nextInt();
		
		System.out.println("输入函数依赖集F：");
		for(int i = 1; i <= num; i++)
		{
			System.out.print("请输入第" + i + "个函数依赖左边的值：");
			String valueLeft = scan.next();
			left.add(valueLeft);
			
			System.out.print("请输入第" + i + "个函数依赖右边的值：");
			String valueRight = scan.next();
			right.add(valueRight);
		}
	}
	
	/**
	 * 输出求得的闭包
	 * @param x 属性集X
	 * @param u 属性集总体U
	 * @return 所求得的闭包
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
	 * 删除字符串中的重复字符
	 * @param str 要进行操作的字符串
	 * @return 删去重复字符后的字符串
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
		
		System.out.println("得到闭包为：" + closure);
		
	}
}