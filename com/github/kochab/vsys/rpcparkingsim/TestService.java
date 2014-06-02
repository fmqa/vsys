package com.github.kochab.vsys.rpcparkingsim;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestService {
    private static String request(ParkingLot p, String method, Object[] args, Class<?>... argTypes) throws Exception {
        final ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
        
        final SerializableMethod m = SerializableMethod.serialize(
            ParkingLotService.class,
            method,
            argTypes
        );
        
        final ObjectOutputStream oos = new ObjectOutputStream(outBytes);
        
        oos.writeObject(m);
        oos.writeObject(args);
        
        final ByteArrayInputStream inBytes = new ByteArrayInputStream(outBytes.toByteArray());
        final ObjectInputStream ois = new ObjectInputStream(inBytes);
        
        final ByteArrayOutputStream outResponseBytes = new ByteArrayOutputStream();
        final ObjectOutputStream oosResponse = new ObjectOutputStream(outResponseBytes);
        
        final ParkingLotService service = new ParkingLotService(p);
        final Session session = new Session(service, ois, oosResponse);
        
        session.serveOnce();
        
        final ObjectInputStream oisCheck = new ObjectInputStream(new ByteArrayInputStream(outResponseBytes.toByteArray()));
        
        final String respStr = (String)oisCheck.readObject();
        
        return respStr;
    }
    
    @Test
    public void testSessionFree() throws Exception {
        ParkingLot p = new SynchronizedParkingLot(5);
        
        assertTrue(request(p, "free", new Object[]{}).equals("5 spot(s) vacant"));
        assertTrue(p.remaining() == 5);
    }
    
    @Test
    public void testSessionIn() throws Exception {
        ParkingLot p = new SynchronizedParkingLot(5);
        
        assertTrue(request(p, "in", new Object[]{1}, Integer.TYPE).equals("1 car(s) parked"));
        assertTrue(p.remaining() == 4);
    }
    
    @Test
    public void testSessionInOut() throws Exception {
        ParkingLot p = new SynchronizedParkingLot(5);
        
        assertTrue(request(p, "in", new Object[]{1}, Integer.TYPE).equals("1 car(s) parked"));
        assertTrue(request(p, "out", new Object[]{1}, Integer.TYPE).equals("1 car(s) unparked"));
        assertTrue(p.remaining() == 5);
    }
    
    @Test
    public void testSessionUnderrun() throws Exception {
        ParkingLot p = new SynchronizedParkingLot(5);
        
        assertTrue(request(p, "out", new Object[]{1}, Integer.TYPE).equals("capacity underrun"));
        assertTrue(p.remaining() == 5);
    }
    
    @Test
    public void testSessionOverrun() throws Exception {
        ParkingLot p = new SynchronizedParkingLot(5);
        
        assertTrue(request(p, "in", new Object[]{6}, Integer.TYPE).equals("capacity overrun"));
        assertTrue(p.remaining() == 5);
    }
}
