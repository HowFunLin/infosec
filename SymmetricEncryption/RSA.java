package SymmetricEncryption;

import java.util.Scanner;

public class RSA
{
	int p = 0, q = 0, e = 0, d = 0, n = 0;
	static int x = -1, y = -1;
	private Scanner scan = new Scanner(System.in);
	
	/**
	 * ������Կ
	 */
	void generateKey()
	{
		//ѡ����������p��q
		primeNumberJudgment();
		
		//��Կ��˽Կ�Ĺ�������
		this.n = this.p * this.q;
		
		//ŷ������
		int euler = (this.p - 1) * (this.q - 1);
		
		//ѡ��������ŷ������������ʵ�����e����Ϊ��Կ��һ����
		this.e = selectE(euler);
		
		//����d����Ϊ˽Կ��һ����
		this.d = extend_gcd(e, euler);
		this.d = this.d < 0 ? this.d + euler : this.d;
	}
	
	/**
	 * ��չŷ������㷨
	 * @param a x�Ĳ���
	 * @param b y�Ĳ���
	 * @return ��õ�x��ֵ
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
	 * ѡ������e 
	 * @param euler ŷ��������ֵ
	 * @return ���������ɸѡ�õ���ѡ�������e
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
	 * ŷ������㷨
	 * @param m ������m
	 * @param n ������n
	 * @return m��n�����Լ��
	 */
	private int gcd(int m, int n)
	{
		if(n == 0) return m; 
	    int r = m % n;
	    return gcd(n,r);
	}
	
	/**
	 * ����������ֽ��������жϲ����������б���
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
	 * �����㷨
	 * @return ���ܵõ�������
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
	 * �����㷨
	 * @return ���ܵõ�������
	 */
	int decrypt()
	{
		System.out.print("Please enter the ciphertext you want to decrypt: ");
		int c = scan.nextInt();
		
		return (int) exp_mod(c, d, n);
	}
	
	/**
	 * ������ȡģa^n mod b
	 * @param a ����
	 * @param n ��
	 * @param b ģ��
	 * @return ������ȡģ������
	 */
	private long exp_mod(long a, long n, long b)
    {
        long t;
        
        if(n == 0) return 1 % b;
        if(n == 1) return a % b;
        
        t = exp_mod(a, n / 2, b);
        t = t * t % b;

        //���n������������һ��a
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