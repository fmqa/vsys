package com.github.kochab.vsys.parkingsim;

import java.util.Random;

public final class CarFactory {
    private CarFactory() {
    }
    
    public static Car createRandomCar(ParkingLot p, int minDel, int maxDel) {
        Random rng = new Random();
        StringBuilder id = new StringBuilder();
        
        id.append((char)(rng.nextInt(26) + 65));
        id.append((char)(rng.nextInt(26) + 65));
        if (rng.nextInt(2) == 1) {
            id.append((char)(rng.nextInt(26) + 65));
        }
        id.append(rng.nextInt(10));
        id.append(rng.nextInt(10));
        id.append(rng.nextInt(10));
        
        return new Car(id.toString(),
                       minDel + rng.nextInt(maxDel - minDel),
                       minDel + rng.nextInt(maxDel - minDel),
                       p);
                          
    }
}