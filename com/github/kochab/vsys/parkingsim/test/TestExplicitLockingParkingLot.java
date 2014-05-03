package com.github.kochab.vsys.parkingsim.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.github.kochab.vsys.parkingsim.ExplicitLockingParkingLot;
import com.github.kochab.vsys.parkingsim.Car;
import com.github.kochab.vsys.parkingsim.CarFactory;

/**
 * Test-Cases for the Java >= 5.0 locks-based parking lot implementation
 * 
 * @author Fadi Moukayed
 */


@RunWith(JUnit4.class)
public class TestExplicitLockingParkingLot {
    private static final long EPSILON = 5;
    
    @Test
    public void testRemainingCapacity() {
        ExplicitLockingParkingLot elpl = new ExplicitLockingParkingLot(3);
        elpl.park(CarFactory.createRandomCar(elpl, 20, 30));
        elpl.park(CarFactory.createRandomCar(elpl, 20, 30));
        assertTrue(elpl.remainingCapacity() == 1);
    }
    
    @Test(expected = IllegalStateException.class)
    public void testIllegalAddState() {
        ExplicitLockingParkingLot elpl = new ExplicitLockingParkingLot(1);
        elpl.park(CarFactory.createRandomCar(elpl, 20, 30));
        elpl.park(CarFactory.createRandomCar(elpl, 20, 30));
    }
    
    @Test(expected = IllegalStateException.class)
    public void testIllegalRemoveState() {
        ExplicitLockingParkingLot elpl = new ExplicitLockingParkingLot(1);
        elpl.unpark(CarFactory.createRandomCar(elpl, 20, 30));
    }
    
    @Test
    public void testDelay() {
        ExplicitLockingParkingLot elpl = new ExplicitLockingParkingLot(1);
        Car c = new Car("0", 10, 20, elpl);
        long startTime = System.currentTimeMillis();
        c.run();
        long endTime = System.currentTimeMillis();
        assertTrue(endTime <= startTime + 30 + EPSILON);
    }
}