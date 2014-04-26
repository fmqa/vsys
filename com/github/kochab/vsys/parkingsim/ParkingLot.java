package com.github.kochab.vsys.parkingsim;

public interface ParkingLot {
    void park(Car c) throws InterruptedException;
    void unpark(Car c);
    int remainingCapacity();
}
