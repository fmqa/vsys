package com.github.kochab.vsys.netparkingsim;

public interface ParkingLot {
    boolean park();
    boolean unpark();
    int remaining();
}
