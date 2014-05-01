package com.github.kochab.vsys.parkingsim;

import java.util.ArrayList;

public class SynchronizedParkingLot implements ParkingLot {
    public SynchronizedParkingLot (int cap) {
        capacity = cap;
        cars = new ArrayList<Car>(cap);
    }
    
    @Override
    public synchronized void park(Car c) {
        if (cars.size() >= capacity) {
            throw new IllegalStateException();
        }
        cars.add(c);
    }
    
    @Override
    public synchronized void unpark(Car c) {
        if (!cars.remove(c)) {
            throw new IllegalStateException();
        }
    }
    
    @Override
    public synchronized int remainingCapacity() {
        return capacity - cars.size();
    }
    
    @Override
    public String toString() {
        return "SynchronizedParkingLot{Capacity=" + remainingCapacity() + "}";
    }
    
    private final int capacity;
    private final ArrayList<Car> cars;
}

 
