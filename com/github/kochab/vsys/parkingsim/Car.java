package com.github.kochab.vsys.parkingsim;

/**
 * A class representing a Car object.
 * 
 * @author Eugen Kinder
 * @author Matthias Siegmund
 * @author Fadi Moukayed
 */

public final class Car implements Runnable {
    /**
     * Constructs a Car with the given id, start-up delay, parking time, 
     * and an associated parking lot.
     */
    public Car(String id, int startDelay, int parkTime, ParkingLot parkingLot) {
        this.id = id;
        this.startDelay = startDelay;
        this.parkTime = parkTime;
        this.parkingLot = parkingLot;
    }
    
    @Override
    public void run() {
        try {
            Thread.sleep(startDelay);
            if (parkingLot.remainingCapacity() > 0) {
                System.out.println(this + " is parking [" + System.currentTimeMillis() + "]");
                parkingLot.park(this);
                Thread.sleep(parkTime);
                parkingLot.unpark(this);
                System.out.println(this + " is leaving [" + System.currentTimeMillis() + "]");
            } else {
                System.out.println(
                    this + 
                    " left after failing to find a parking space [" + 
                    System.currentTimeMillis() + 
                    "]"
                );
            }
        } catch (InterruptedException e) {
            System.err.println(this + " has been interrupted!");
        }
    }
    
    @Override
    public String toString() {
        return "Car" + 
               "{id=" + id + 
               ",startDelay=" + startDelay + 
               ",parkTime=" + parkTime +
               ",parkingLot=" + parkingLot + "}";
    }
    
    private String id;
    private int startDelay;
    private int parkTime;
    private ParkingLot parkingLot;
}
