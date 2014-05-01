package com.github.kochab.vsys.parkingsim.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.github.kochab.vsys.parkingsim.BlockingQueueParkingLot;
import com.github.kochab.vsys.parkingsim.Car;
import com.github.kochab.vsys.parkingsim.CarFactory;

@RunWith(JUnit4.class)
public class TestBlockingQueueParkingLot {
    private static final long EPSILON = 5;

    @Test
    public void testRemainingCapacity() {
        BlockingQueueParkingLot bqpl = new BlockingQueueParkingLot(3);
        bqpl.park(CarFactory.createRandomCar(bqpl, 20, 30));
        bqpl.park(CarFactory.createRandomCar(bqpl, 20, 30));
        assertTrue(bqpl.remainingCapacity() == 1);
    }
    
    @Test(expected = IllegalStateException.class)
    public void testIllegalAddState() {
        BlockingQueueParkingLot bqpl = new BlockingQueueParkingLot(1);
        bqpl.park(CarFactory.createRandomCar(bqpl, 20, 30));
        bqpl.park(CarFactory.createRandomCar(bqpl, 20, 30));
    }
    
    @Test(expected = IllegalStateException.class)
    public void testIllegalRemoveState() {
        BlockingQueueParkingLot bqpl = new BlockingQueueParkingLot(1);
        bqpl.unpark(CarFactory.createRandomCar(bqpl, 20, 30));
    }
    
    @Test
    public void testDelay() {
        BlockingQueueParkingLot bqpl = new BlockingQueueParkingLot(1);
        Car c = new Car("0", 10, 20, bqpl);
        long startTime = System.currentTimeMillis();
        c.run();
        long endTime = System.currentTimeMillis();
        assertTrue(endTime <= startTime + 30 + EPSILON);
    }
}