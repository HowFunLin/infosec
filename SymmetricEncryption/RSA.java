package SymmetricEncryption;

import java.util.Scanner;

public class RSA
{
	int p = 0, q = 0, e = 0, d = 0, n = 0;
	static int x = -1, y = -1;
	private Scanner scan = new Scanner(System.in);
	
	/**
	 * 生成密钥
	 */
	void generateKey()
	{
		//选择两个素数p和q
		primeNumberJudgment();
		
		//公钥和私钥的公共部分
		this.n = this.p * this.q;
		
		//欧拉函数
		int euler = (this.p - 1) * (this.q - 1);
		
		//选择满足与欧拉函数结果互质的整数e，作为公钥的一部分
		this.e = selectE(euler);
		
		//计算d，作为私钥的一部分
		this.d = extend_gcd(e, euler);
		this.d = this.d < 0 ? this.d + euler : this.d;
	}
	
	/**
	 * 扩展欧几里得算法
	 * @param a x的参数
	 * @param b y的参数
	 * @return 求得的x的值
	 */
	private int extend_gcd(int a,int b) 
	{
		if(b == 0) 
		{
			x = 1;
			y = 0;
			return x;
		}
		else 
		{
			extend_gcd(b, a % b);
			int t = x;
			x = y;
			y = t - a / b * y;
			return x;			
		}		
	}
	
	/**
	 * 选择整数e 
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
	    int r = m % n;
	    return gcd(n,r);
	}
	
	/**
	 * 对输入的数字进行素数判断并对素数进行保存
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
	
	/**
	 * 加密算法
	 * @return 加密得到的密文
	 */
	int encrypt()
	{
		System.out.print("Please enter the plaintext you want to encrypt: ");
		int m = scan.nextInt();
		
		if(m >= this.n)
		{
			System.out.println("Try again! The plaintext you enter is illegal!");
			encrypt();
		}
		
		return (int) exp_mod(m, e, n);
	}
	
	/**
	 * 解密算法
	 * @return 解密得到的明文
	 */
	int decrypt()
	{
		System.out.print("Please enter the ciphertext you want to decrypt: ");
		int c = scan.nextInt();
		
		return (int) exp_mod(c, d, n);
	}
	
	/**
	 * 快速幂取模a^n mod b
	 * @param a 底数
	 * @param n 幂
	 * @param b 模数
	 * @return 快速幂取模运算结果
	 */
	private long exp_mod(long a, long n, long b)
    {
        long t;
        
        if(n == 0) return 1 % b;
        if(n == 1) return a % b;
        
        t = exp_mod(a, n / 2, b);
        t = t * t % b;

        //如果n是奇数，需多乘一次a
        if((n&1) == 1) t = t * a % b;

        return t;
    }
	
	public static void main(String[] args)
	{
		RSA rsa = new RSA();
		rsa.generateKey();
		
		System.out.println();
		System.out.println("The public key KU = {" + rsa.e + ", " + rsa.n + "}");
		System.out.println("The public key KR = {" + rsa.d + ", " + rsa.n + "}");
		System.out.println();
		
		int ciphertext = rsa.encrypt();
		System.out.println("The ciphertext after encrytion is " + ciphertext);
		System.out.println();
		
		int plaintext = rsa.decrypt();
		System.out.println("The plaintext after decrytion is " + plaintext);
	}
}