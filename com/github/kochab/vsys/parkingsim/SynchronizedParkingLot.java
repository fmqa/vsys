package com.github.kochab.vsys.parkingsim;

import java.util.ArrayList;

/**
 * A Parking-Lot implementation using synchronized methods.
 * 
 * @author Eugen Kinder
 */

public class SynchronizedParkingLot implements ParkingLot {
    public SynchronizedParkingLot(int cap) {
        capacity = cap;
        cars = new ArrayList<Car>(cap);
    }
    
    @Override
    public synchronized void park(Car c) {
        if (cars.size() >= capacity) {
            throw new IllegalStateException("Attempt to park in full parking lot");
        }
        cars.add(c);
    }
    
    @Override
    public synchronized void unpark(Car c) {
        if (!cars.remove(c)) {
            throw new IllegalStateException("Unparking of non-parked car");
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

 
