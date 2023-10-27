/*
1.Write a java program that implements a Bus Reservation System using multithreading.
Create n no of customer to reserve and cancel seat.
 */

package javaex8;
//import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author VISHWA
 */
public class Busreservation 
{
    public static void main(String[] args)
    {
        int totalSeats = 10;
        Bus bus = new Bus(totalSeats);
        int numCustomers = 5;
        ExecutorService executor = Executors.newFixedThreadPool(numCustomers);
        for (int i = 1; i <= numCustomers; i++)
        {
            executor.execute(new Customer(bus, i));
        }
        executor.shutdown();
    }    
}

class Bus {
    private boolean[] seats;
    private int totalSeats;
    public Bus(int totalSeats)
    {
        this.totalSeats = totalSeats;
        seats = new boolean[totalSeats];
    }
    public synchronized boolean reserveSeat(int seatNumber)
    {
        if (seatNumber < 1 || seatNumber > totalSeats)
        {
            System.out.println("Invalid seat number: " + seatNumber);
            return false;
        }
        if (seats[seatNumber - 1])
        {
            System.out.println("Seat " + seatNumber + " is already reserved.");
            return false;
        }
        seats[seatNumber - 1] = true;
        System.out.println("Seat " + seatNumber + " reserved by " + Thread.currentThread().getName());
        return true;
    }
    public synchronized boolean cancelSeat(int seatNumber)
    {
        if (seatNumber < 1 || seatNumber > totalSeats)
        {
            System.out.println("Invalid seat number: " + seatNumber);
            return false;
        }
        if (!seats[seatNumber - 1])
        {
            System.out.println("Seat " + seatNumber + " is not reserved.");
            return false;
        }
        seats[seatNumber - 1] = false;
        System.out.println("Seat " + seatNumber + " canceled by " + Thread.currentThread().getName());
        return true;
    }
}

class Customer implements Runnable {
    private Bus bus;
    private int seatNumber;
    public Customer(Bus bus, int seatNumber)
    {
        this.bus = bus;
        this.seatNumber = seatNumber;
    }
    @Override
    public void run()
    {
        bus.reserveSeat(seatNumber);
        try 
        {
            Thread.sleep(2000);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
        bus.cancelSeat(seatNumber);
    }
}
