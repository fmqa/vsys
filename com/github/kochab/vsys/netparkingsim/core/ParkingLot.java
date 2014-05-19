package com.github.kochab.vsys.netparkingsim.core;

/**
 * A Parking lot. The user of this interface can add/remove
 * Car objects from the Parking Lot and display the remaining capacity
 * of it.
 * 
 * @author Matthias Siegmund
 * @author Eugen Kinder
 * @author Fadi Moukayed
 */


public interface ParkingLot {
    /**
     * Add a Car to the parking lot.
     */
    boolean park();
    
    /**
     * Remove a Car from the parking lot.
     */
    boolean unpark();
    
    /**
     * Returns the number of remaining parking spots in this
     * parking lot.
     */
    int remaining();
}
