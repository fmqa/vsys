package com.github.kochab.vsys.netparkingsim.server;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import com.github.kochab.vsys.netparkingsim.core.ParkingLot;

/**
 * A Parking request handler that prints incoming requests.
 * 
 * All requests handled by this class also modify the associated
 * Parking lot object accordingly.
 *
 * @author Matthias Siegmund
 *
 */

public class ParkingLotRequestHandler implements RequestHandler {
    /**
     * Constructs a Parking request handler for the given Parking lot
     */
    public ParkingLotRequestHandler(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }
    
    @Override
    public void handleFree(OutputStream out) throws IOException {
        final PrintWriter pw = new PrintWriter(out);
        pw.println(parkingLot.remaining());
        pw.flush();
    }
    
    @Override
    public void handleIn(OutputStream out) throws IOException {
        final PrintWriter pw = new PrintWriter(out);
        pw.println(parkingLot.park() ? "ok" : "fail");
        pw.flush();
    }
    
    @Override
    public void handleOut(OutputStream out) throws IOException {
        final PrintWriter pw = new PrintWriter(out);
        pw.println(parkingLot.unpark() ? "ok" : "fail");
        pw.flush();
    }
    
    @Override
    public void handleQuit(OutputStream out) throws IOException {
        final PrintWriter pw = new PrintWriter(out);
        pw.println("ok");
        pw.flush();
        System.exit(0);
    }
    
    @Override
    public void handleUnknown(OutputStream out, String data) throws IOException {
        final PrintWriter pw = new PrintWriter(out);
        pw.println("Unknown request: " + data);
        pw.flush();
    }
    
    private final ParkingLot parkingLot;
}
