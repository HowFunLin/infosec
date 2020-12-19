package Armstrong;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * 对F进行极小化处理，获得最小依赖集
 * @author Riyad
 */
public class MinimalCover
{
	private Scanner scan = new Scanner(System.in);
	
	ArrayList<String> left = new ArrayList<String>();
	ArrayList<String> right = new ArrayList<String>();
	
	/**
	 * 输入属性集总体U
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
	 * 对所有函数依赖右边的值进行拆分
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
	 * 检查F中各函数依赖并对符合条件的函数依赖进行删减
	 * @param u 属性集总体U
	 */
	private void check(String u)
	{
		ArrayList<String> oldLeft = new ArrayList<String>();
		ArrayList<String> oldRight = new ArrayList<String>();
		
		//对原来的函数依赖进行备份
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
			
			if(closure.contains(a)) //如果A属于X的闭包，则原来的函数依赖也需删去对应的值，以保持同步
			{
				oldLeft.remove(i);
				oldRight.remove(i);
				
				continue;
			}	
			else //否则，则复原为原来的函数依赖
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
	 * 检查F中各函数依赖左边的值，并对符合条件的值进行替换
	 * @param u 属性集总体U
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
					
					//去掉原来X中的Bj
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
	 * 输出求得的闭包
	 * @param x 属性集X
	 * @param u 属性集总体U
	 * @return 所求得的闭包
	 */
	private String outputClosure(String x, String u)
	{
		String lastOne = null;
		
		ArrayList<String> oldLeft = new ArrayList<String>();
		ArrayList<String> oldRight = new ArrayList<String>();
		
		//由于求闭包操作会删去原来函数依赖中的部分函数依赖，需对原来的函数依赖进行备份
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
		
		//操作完成后，清除现存的函数依赖，恢复原函数依赖
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
		MinimalCover mc = new MinimalCover();
		
		String u = mc.inputU();
		mc.inputF();
		
		mc.checkY();
		mc.check(u);
		mc.checkX(u);
		
		System.out.print("F的最小依赖集Fm = {");
		for(int i = 0; i < mc.left.size(); i++)
		{
			System.out.print(" " + mc.left.get(i) + "->" + mc.right.get(i) + " ");
		}
		System.out.println("}");
	}
}