package com.github.kochab.vsys.netparkingsim;

import java.util.List;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.UnknownHostException;
import com.github.kochab.vsys.netparkingsim.client.CommandDispatcher;
import com.github.kochab.vsys.netparkingsim.client.CommandDispatchers;

class ParkingLotClientMain {
    private ParkingLotClientMain() {
    }
    
    public static void main(String[] args) throws IOException, UnknownHostException {
        if (args.length < 1) {
            System.err.println("Usage: ParkingClient <IP:PORT> ...");
            return;
        }
        
        List<CommandDispatcher> dispatchers = CommandDispatchers.createUnicastDispatchers(Arrays.asList(args));
        
        System.out.print("Hosts: ");
        for (int i = 0; i < args.length; ++i) {
            System.out.print((i + 1) + ". " + args[i] + "; ");
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
                        System.out.println("<[" + args[hostNumber - 1] + "] " +
                            dispatchers.get(hostNumber - 1).execute(command)
                        );
                        if (command.equals("quit")) {
                            System.exit(0);
                        }
                    } else {
                        System.err.println("Invalid host identifier: " + hostNumber);
                    }
                } else {
                    for (int i = 0; i < dispatchers.size(); ++i) {
                        String resp = dispatchers.get(i).execute(command);
                        System.out.println("<[" + args[i] + "] " + resp);
                        if (resp.equals("ok") && !command.equals("quit")) {
                            break;
                        }
                    }
                    if (command.equals("quit")) {
                        System.exit(0);
                    }
                }
            }
            System.out.print("> ");
        }
        
        stdIn.close();
    }
}