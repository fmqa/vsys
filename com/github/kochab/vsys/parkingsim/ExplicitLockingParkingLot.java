package com.github.kochab.vsys.parkingsim;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ExplicitLockingParkingLot implements ParkingLot {
    public ExplicitLockingParkingLot(int cap) {
        capacity = cap;
        cars = new ArrayList<Car>(cap);
    }
    
    @Override
    public void park(Car c) {
        lock.lock();
        try {
            if (cars.size() >= capacity) {
                throw new IllegalStateException();
            }
            cars.add(c);
        } finally {
            lock.unlock();
        }
    }
    
    @Override
    public void unpark(Car c) {
        lock.lock();
        try {
            if (!cars.remove(c)) {
                throw new IllegalStateException();
            }
        } finally {
            lock.unlock();
        }
    }
    
    @Override
    public int remainingCapacity() {
        lock.lock();
        try {
            return capacity - cars.size();
        } finally {
            lock.unlock();
        }
    }
    
    @Override
    public String toString() {
        return "ExplicitLockingParkingLot{Capacity=" + remainingCapacity() + "}";
    }
    
    private final Lock lock = new ReentrantLock();
    private final int capacity;
    private final ArrayList<Car> cars;
}

