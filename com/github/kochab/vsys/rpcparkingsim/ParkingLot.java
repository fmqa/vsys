package com.github.kochab.vsys.rpcparkingsim;

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
     * Add n Cars to the parking lot.
     */
    boolean park(int n);
    
    /**
     * Remove a Car from the parking lot.
     */
    boolean unpark();
    
    /**
     * Remove n Cars from the parking lot.
     */
    boolean unpark(int n);
    
    /**
     * Returns the number of remaining parking spots in this
     * parking lot.
     */
    int remaining();
}
