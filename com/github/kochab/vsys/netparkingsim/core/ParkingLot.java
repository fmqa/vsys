package com.github.kochab.vsys.netparkingsim.core;

public interface ParkingLot {
    boolean park();
    boolean unpark();
    int remaining();
}
