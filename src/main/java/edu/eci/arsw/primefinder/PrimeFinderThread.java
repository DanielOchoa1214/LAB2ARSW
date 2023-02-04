package edu.eci.arsw.primefinder;

//import sun.awt.windows.ThemeReader;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PrimeFinderThread extends Thread{

	
	int a,b;

    private boolean isWait;
	private final List<Integer> primes;
	
	public PrimeFinderThread(int a, int b, ArrayList<Integer> primes) {
		super();
        this.isWait = false;
        this.primes = primes;
		this.a = a;
		this.b = b;
	}

    @Override
	public void run(){
        for(int i = a; i < b; i++) {
            synchronized(primes) {
                if(isPrime(i)) {
                    primes.add(i);
                    //System.out.println(i);
                }
            }
        }
	}

    public void changeIsWait() {
        System.out.println(isWait);
        isWait = !isWait;
        System.out.println(isWait);
    }
	
	boolean isPrime(int n) {
	    boolean ans;
            if (n > 2) { 
                ans = n%2 != 0;
                for(int i = 3;ans && i*i <= n; i+=2 ) {
                    ans = n % i != 0;
                }
            } else {
                ans = n == 2;
            }
	    return ans;
	}

	public List<Integer> getPrimes() {
		return primes;
	}
	
}
