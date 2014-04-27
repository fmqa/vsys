package com.github.kochab.vsys.parkingsim;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

public class BlockingQueueParkingLot implements ParkingLot {
    public BlockingQueueParkingLot(int capacity) {
        q = new ArrayBlockingQueue<Car>(capacity);
    }
    
    @Override
    public void park(Car c) throws InterruptedException { 
        q.put(c); 
    }
    
    @Override
    public void unpark(Car c) { 
        q.remove(c); 
    }
    
    @Override
    public int remainingCapacity() { return q.remainingCapacity(); }
    
    @Override
    public String toString() {
        return "ParkingLot{Capacity=" + remainingCapacity() + "}";
    }
    
    private final BlockingQueue<Car> q;
}
