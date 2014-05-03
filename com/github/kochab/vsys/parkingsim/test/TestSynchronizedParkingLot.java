package com.github.kochab.vsys.parkingsim.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.github.kochab.vsys.parkingsim.SynchronizedParkingLot;
import com.github.kochab.vsys.parkingsim.Car;
import com.github.kochab.vsys.parkingsim.CarFactory;

/**
 * Test-Cases for the synchronized parking lot implementation
 * 
 * @author Fadi Moukayed
 * @author Eugen Kinder
 */


@RunWith(JUnit4.class)
public class TestSynchronizedParkingLot {
    private static final long EPSILON = 5;
    
    @Test
    public void testRemainingCapacity() {
        SynchronizedParkingLot spl = new SynchronizedParkingLot(3);
        spl.park(CarFactory.createRandomCar(spl, 20, 30));
        spl.park(CarFactory.createRandomCar(spl, 20, 30));
        assertTrue(spl.remainingCapacity() == 1);
    }
    
    @Test(expected = IllegalStateException.class)
    public void testIllegalAddState() {
        SynchronizedParkingLot spl = new SynchronizedParkingLot(1);
        spl.park(CarFactory.createRandomCar(spl, 20, 30));
        spl.park(CarFactory.createRandomCar(spl, 20, 30));
    }
    
    @Test(expected = IllegalStateException.class)
    public void testIllegalRemoveState() {
        SynchronizedParkingLot spl = new SynchronizedParkingLot(1);
        spl.unpark(CarFactory.createRandomCar(spl, 20, 30));
    }
    
    @Test
    public void testDelay() {
        SynchronizedParkingLot spl = new SynchronizedParkingLot(1);
        Car c = new Car("0", 10, 20, spl);
        long startTime = System.currentTimeMillis();
        c.run();
        long endTime = System.currentTimeMillis();
        assertTrue(endTime <= startTime + 30 + EPSILON);
    }
}