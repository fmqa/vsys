package com.github.kochab.vsys.netparkingsim.core;

public class SynchronizedParkingLot implements ParkingLot {
    public SynchronizedParkingLot(int capacity) {
        this.capacity = capacity;
    }
    
    @Override
    public synchronized boolean park() {
        if (count < capacity) {
            ++count;
            return true;
        }
        return false;
    }
    
    @Override
    public synchronized boolean unpark() {
        if (count > 0) {
            --count;
            return true;
        }
        return false;
    }
    
    @Override
    public synchronized int remaining() {
        return capacity - count;
    }
    
    private final int capacity;
    private int count;
}

