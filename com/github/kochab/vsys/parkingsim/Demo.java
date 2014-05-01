package com.github.kochab.vsys.parkingsim;

import java.util.Random;
import java.util.ArrayList;

final class Demo {
    private Demo() {
    }
    
    public static void main(String[] args) {
        int numParkingLots = 3;
        
        if (args.length == 1) {
            try {
                numParkingLots = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid argument: " + args[0]);
                System.exit(1);
            }
            if (numParkingLots < 1) {
                System.err.println("Number of parking lots must be >= 1");
                System.exit(1);
            }
        }
        
        ArrayList<ParkingLot> pLots = new ArrayList<ParkingLot>(numParkingLots);
        
        Random r = new Random();
        for (int i = 0; i < numParkingLots; ++i) {
            switch (r.nextInt(3)) {
                case 0: pLots.add(new BlockingQueueParkingLot(100)); break;
                case 1: pLots.add(new SynchronizedParkingLot(100)); break;
                case 2: pLots.add(new ExplicitLockingParkingLot(100)); break;
            }
        }
        
        System.out.println("===== PARKING LOTS SELECTED =====");
        System.out.println(pLots);
        System.out.println("=================================");
        
        for (ParkingLot p : pLots) {
            System.out.println("Parking lot " + p);
            System.out.println("=================================");
            for (int i = 0; i < 100; ++i) {
                (new Thread(CarFactory.createRandomCar(p, 26, 50))).start();
            }
        }
    }
}
