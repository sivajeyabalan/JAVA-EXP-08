/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javaapplication1;
import java.util.*;


/**
 *
 * @author sivajb
 */
public class oddoreven{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner obj = new Scanner(System.in);
        int a, b;
        System.out.println("Enter the starting number");
        a=obj.nextInt();
        System.out.println("Enter the ending number");
        b=obj.nextInt();
        numberprint n = new numberprint();
        odd t1 = new odd(a,b,n);
        even t2 = new even(a,b,n);
        t1.start();
        t2.start();
        
    }
    
}
class numberprint {
    boolean v = false;

    public synchronized void print1(int a) throws InterruptedException {
        while (v) {
            wait();
        }
        System.out.println(a);
        v = true;
        notifyAll();
    }

    public synchronized void print2(int a) throws InterruptedException {
        while (!v) {
            wait();
        }
        System.out.println(a);
        v = false;
        notifyAll();
    }
}

class odd extends Thread {
    int b, a;
    numberprint n;

    odd(int a, int b, numberprint n) {
        this.n = n;
        this.b = b;
        this.a = a;
    }

    public void run() {
        for (int i = a; i <= b; i++) {
            if (i % 2 == 1) {
                try {
                    n.print1(i);
                } catch (InterruptedException e) {
                }
            }
        }
    }
}

class even extends Thread {
    int b, a;
    numberprint n;

    even(int a, int b, numberprint n) {
        this.n = n;
        this.b = b;
        this.a = a;
    }

    public void run() {
        for (int i = a; i <= b; i++) {
            if (i % 2 == 0) {
                try {
                    n.print2(i);
                } catch (InterruptedException e) {
                }
            }
        }
    }
}
 