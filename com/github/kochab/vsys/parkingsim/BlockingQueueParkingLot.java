package com.github.kochab.vsys.parkingsim;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

public class BlockingQueueParkingLot implements ParkingLot {
    public BlockingQueueParkingLot(int capacity) {
        q = new ArrayBlockingQueue<Car>(capacity);
    }
    
    @Override
    public void park(Car c) { 
        if (!q.offer(c)) {
            throw new IllegalStateException();
        }
    }
    
    @Override
    public void unpark(Car c) { 
        if (!q.remove(c)) {
            throw new IllegalStateException();
        }
    }
    
    @Override
    public int remainingCapacity() { 
        return q.remainingCapacity(); 
    }
    
    @Override
    public String toString() {
        return "BlockingQueueParkingLot{Capacity=" + remainingCapacity() + "}";
    }
    
    private final BlockingQueue<Car> q;
}
