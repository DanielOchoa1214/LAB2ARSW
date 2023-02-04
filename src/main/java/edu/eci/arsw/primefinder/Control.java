/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.primefinder;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 */
public class Control extends Thread {
    
    private final static int NTHREADS = 3;
    private final static int MAXVALUE = 30000000;
    private final static int TMILISECONDS = 5000;

    private Scanner scanner = new Scanner(System.in);
    private final int NDATA = MAXVALUE / NTHREADS;

    private PrimeFinderThread pft[];

    private final ArrayList<Integer> primes = new ArrayList<>();
    private Control() {
        super();
        this.pft = new  PrimeFinderThread[NTHREADS];
        int i;
        for(i = 0;i < NTHREADS - 1; i++) {
            PrimeFinderThread elem = new PrimeFinderThread(i*NDATA, (i+1)*NDATA, primes);
            pft[i] = elem;
        }
        pft[i] = new PrimeFinderThread(i*NDATA, MAXVALUE + 1, primes);
    }
    
    public static Control newControl() {
        return new Control();
    }

    private boolean isAliveThread() {
        for (Thread thread : pft) {
            if(thread.isAlive()){
                return true;
            }
        }
        return false;
    }
    @Override
    public void run() {
        for(int i = 0;i < NTHREADS;i++ ) {
            pft[i].start();
        }
        while (this.isAliveThread()) {
            synchronized(primes) {
                try {
                    primes.wait(TMILISECONDS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(primes.size());
                System.out.println("Press Enter Key to continue...");
                scanner.nextLine();
            }
        }
    }
}
