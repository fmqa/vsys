package com.github.kochab.vsys.parkingsim;

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
     * Adds a Car to the parking lot.
     */
    void park(Car c);
    
    /**
     * Remove a Car from the parking lot.
     */
    void unpark(Car c);
    
    /**
     * Returns the number of remaining parking spots in this
     * parking lot.
     */
    int remainingCapacity();
}
