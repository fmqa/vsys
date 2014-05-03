package com.github.kochab.vsys.parkingsim;

import java.util.Random;

/**
 * Auxiliary methods for creating car Objects.
 * 
 * @author Eugen Kinder
 * @author Matthias Siegmund
 * @author Fadi Moukayed
 */

public final class CarFactory {
    private CarFactory() {
    }
    
    
    /**
     * Creates a Car associated with the given parking lot with a random ID, 
     * and a random parking- and start-up delay in the range [minDel, maxDel)
     * 
     * @param p The parking lot the constructed Car object is associated with
     * @param minDel The minimum delay value for both warming-up and parking
     * @param maxDel The maximum delay value for both warming-up and parking
     * @return A randomly-initialized Car object with the above constraints
     */
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