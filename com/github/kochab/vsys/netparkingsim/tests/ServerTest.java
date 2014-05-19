package com.github.kochab.vsys.netparkingsim.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import com.github.kochab.vsys.netparkingsim.core.SynchronizedParkingLot;
import com.github.kochab.vsys.netparkingsim.server.RequestDispatcher;
import com.github.kochab.vsys.netparkingsim.server.ParkingLotRequestHandler;

/**
 * Tests for core server functionality.
 *
 * @author Matthias Siegmund
 * @author Eugen Kinder
 *
 */

@RunWith(JUnit4.class)
public class ServerTest {
    /**
     * Test the parking functionality.
     */
    @Test
    public void parkTest() throws IOException, UnsupportedEncodingException {
        SynchronizedParkingLot spl = new SynchronizedParkingLot(1);
        RequestDispatcher rd = new RequestDispatcher(new ParkingLotRequestHandler(spl));
        
        ByteArrayInputStream is = new ByteArrayInputStream("in\n".getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        
        rd.apply(is, os);
        
        assertTrue(os.toString("UTF-8").equals("ok\n"));
    }
    
    /**
     * Test the unparking functionality.
     */
    @Test
    public void unparkTest() throws IOException, UnsupportedEncodingException {
        SynchronizedParkingLot spl = new SynchronizedParkingLot(1);
        RequestDispatcher rd = new RequestDispatcher(new ParkingLotRequestHandler(spl));
        
        ByteArrayInputStream is = new ByteArrayInputStream("in\nout\n".getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        
        rd.apply(is, os);
        rd.apply(is, os);
        
        assertTrue(os.toString("UTF-8").equals("ok\nok\n"));
    }
    
    /**
     * Test an invalid unparking operation.
     */
    @Test
    public void illegalUnparkTest() throws IOException, UnsupportedEncodingException {
        SynchronizedParkingLot spl = new SynchronizedParkingLot(1);
        RequestDispatcher rd = new RequestDispatcher(new ParkingLotRequestHandler(spl));
        
        ByteArrayInputStream is = new ByteArrayInputStream("in\nout\nout\n".getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        
        rd.apply(is, os);
        rd.apply(is, os);
        rd.apply(is, os);
        
        assertTrue(os.toString("UTF-8").equals("ok\nok\nfail\n"));
    }
    
    /**
     * Test an invalid parking operation.
     */
    @Test
    public void illegalParkTest() throws IOException, UnsupportedEncodingException {
        SynchronizedParkingLot spl = new SynchronizedParkingLot(1);
        RequestDispatcher rd = new RequestDispatcher(new ParkingLotRequestHandler(spl));
        
        ByteArrayInputStream is = new ByteArrayInputStream("in\nin\n".getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        
        rd.apply(is, os);
        rd.apply(is, os);
        
        assertTrue(os.toString("UTF-8").equals("ok\nfail\n"));
    }
}
