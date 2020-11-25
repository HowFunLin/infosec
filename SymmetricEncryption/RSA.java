package SymmetricEncryption;

import java.util.Scanner;

public class RSA
{
	int p = 0, q = 0, e = 0, d = 0, n = 0;
	private Scanner scan = new Scanner(System.in);
	
	void generateKey()
	{
		primeNumberJudgment();
		int euler = (this.p - 1) * (this.q - 1);
		this.e = selectE(euler);
	}
	
	/**
	 *选择整数e 
	 * @param euler 欧拉函数的值
	 * @return 对输入进行筛选得到的选择的整数e
	 */
	private int selectE(int euler)
	{
		System.out.print("Please enter the number e you choose: ");
		int temp = scan.nextInt();
		
		if(gcd(euler, temp) == 1) return temp;
		else
		{
			System.out.println("Try again! The number e you choose is illegal!");
			return selectE(euler);
		}
	}
	
	/**
	 * 欧几里得算法
	 * @param m 正整数m
	 * @param n 正整数n
	 * @return m和n的最大公约数
	 */
	private int gcd(int m, int n)
	{
		if(n == 0) return m; 
	    int r = m%n;
	    return gcd(n,r);
	}
	
	/**
	 * 选择两个素数p和q
	 * 对输入的数字进行素数判断
	 */
	private void primeNumberJudgment()
	{
		System.out.print("Please enter the number p or q you choose: ");
		int temp = scan.nextInt();
		
		int root = (int) Math.sqrt(temp);
		boolean isPrime = true;
		
		for(int i = 2; i <= root; i++)
		{
			if(temp % i == 0)
			{
				isPrime = false;
				break;
			}
		}
		
		if(isPrime == false)
		{
			System.out.println("Try again! The number you choose is not prime number!");
			primeNumberJudgment();
		}
		else
		{
			if(this.p == 0) 
			{
				this.p = temp;
				primeNumberJudgment();
			}
			else
			{
				if(this.q == 0) this.q = temp;
			}
		}
	}
	
	public static void main(String[] args)
	{
		RSA rsa = new RSA();
		rsa.generateKey();
		System.out.println(rsa.p + " " + rsa.q + " " + rsa.e);
	}
}