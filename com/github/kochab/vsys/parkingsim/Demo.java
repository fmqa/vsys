package com.github.kochab.vsys.parkingsim;

class Demo {
    private Demo() {
    }
    
    public static void main(String[] args) {
        ParkingLot pl = new BlockingQueueParkingLot(10);
        for (int i = 0; i < 100; ++i) {
            (new Thread(CarFactory.createRandomCar(pl, 26, 50))).start();
        }
    }
}
