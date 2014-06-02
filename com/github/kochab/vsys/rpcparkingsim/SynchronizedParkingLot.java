package com.github.kochab.vsys.rpcparkingsim;

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
    public synchronized boolean park(int n) {
        if (count + n <= capacity) {
            count += n;
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
    public synchronized boolean unpark(int n) {
        if (count - n >= 0) {
            count -= n;
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

