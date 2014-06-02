package com.github.kochab.vsys.rpcparkingsim;

/**
 * Parking lot facade.
 *
 * @author Matthias Siegmund
 */

public class ParkingLotService {
    public ParkingLotService(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }
    
    public String in(int n) {
        if (parkingLot.park(n)) {
            return n + " car(s) parked";
        } else {
            return "capacity overrun";
        }
    }

    public String out(int n) {
        if (parkingLot.unpark(n)) {
            return n + " car(s) unparked";
        } else {
            return "capacity underrun";
        }
    }
    
    public String free() {
        return parkingLot.remaining() + " spot(s) vacant";
    }
    
    // Erweiterug
    public String hello() {
        return "Hallo Welt!";
    }
    
    public void exit() {
        System.exit(0);
    }
    
    private final ParkingLot parkingLot;
} 
