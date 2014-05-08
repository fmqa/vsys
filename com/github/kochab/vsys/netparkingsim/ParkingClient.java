package com.github.kochab.vsys.netparkingsim;

import java.util.StringTokenizer;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

class ParkingClient {
    private ParkingClient() {
    }
    
    private static void dispatchRequest(String host, int port, String command) {
        try {
            Socket csock = new Socket(host, port);
            PrintWriter out = new PrintWriter(csock.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(csock.getInputStream())
            );
            
            out.println(command);
            if (command.equals("quit")) {
                System.exit(0);
            }
            System.out.println("<[" + host + ":" + port + "] " + in.readLine());
            out.close();
            in.close();
            csock.close();
        } catch (UnknownHostException e) {
            System.err.println("Unkown host: " + host);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("I/O error on host connection: " + host);
            System.exit(1);
        }
    }
    
    private static void dispatchRequest(String[] hosts, int[] ports, String command) {
        int i = 0;
        try {
            for (i = 0; i < hosts.length; ++i) {
                Socket csock = new Socket(hosts[i], ports[i]);
                PrintWriter out = new PrintWriter(csock.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(csock.getInputStream())
                );
            
                out.println(command);
                String resp = null;
                
                if (!command.equals("quit")) {
                    resp = in.readLine();
                    System.out.println("<[" + hosts[i] + ":" + ports[i] + "] " + resp);
                }
                
                out.close();
                in.close();
                csock.close();
                
                if (!command.equals("quit") && resp.equals("ok")) {
                    break;
                }
            }
            
            if (command.equals("quit")) {
                System.exit(0);
            }
        } catch (UnknownHostException e) {
            System.err.println("Unkown host: " + hosts[i]);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("I/O error on host connection: " + hosts[i]);
            System.exit(1);
        }
    }
    
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Usage: ParkingClient <IP:PORT> ...");
            return;
        }
        
        String[] hosts = new String[args.length];
        int[] ports = new int[args.length];
        
        for (int i = 0; i < args.length; ++i) {
            StringTokenizer tokenizer = new StringTokenizer(args[i], ":");
            while (tokenizer.hasMoreTokens()) {
                hosts[i] = tokenizer.nextToken();
                if (tokenizer.hasMoreTokens()) {
                    try {
                        ports[i] = Integer.valueOf(tokenizer.nextToken());
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid IP:PORT pair: " + args[i]);
                        System.exit(1);
                    }
                } else {
                    System.err.println("Invalid IP:PORT pair: " + args[i]);
                    System.exit(1);
                }
            }
        }
        
        System.out.print("Hosts: ");
        for (int i = 0; i < args.length; ++i) {
            System.out.print((i + 1) + ". " + hosts[i] + ":" + ports[i] + "; ");
        }
        System.out.println();
        
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String input;
        
        System.out.print("> ");
        while ((input = stdIn.readLine()) != null) {
            StringTokenizer tokenizer = new StringTokenizer(input);
            if (tokenizer.hasMoreTokens()) {
                String command = tokenizer.nextToken();
                if (tokenizer.hasMoreTokens()) {
                    int hostNumber = -1;
                    try {
                        hostNumber = Integer.valueOf(tokenizer.nextToken());
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid host identifier");
                    }
                    if (hostNumber > 0) {
                        dispatchRequest(hosts[hostNumber - 1], 
                                        ports[hostNumber - 1], 
                                        command);
                    } else {
                        System.err.println("Invalid host identifier: " + hostNumber);
                    }
                } else {
                    dispatchRequest(hosts, ports, command);
                }
            }
            System.out.print("> ");
        }
        
        stdIn.close();
    }
}