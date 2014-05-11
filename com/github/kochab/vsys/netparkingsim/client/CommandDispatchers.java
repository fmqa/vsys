package com.github.kochab.vsys.netparkingsim.client;

import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.net.InetAddress;
import java.net.UnknownHostException;

public final class CommandDispatchers {
    private CommandDispatchers() {}
    
    public static List<CommandDispatcher> 
    createUnicastDispatchers(Iterable<String> pairs) throws UnknownHostException {
        ArrayList<CommandDispatcher> displ = new ArrayList<CommandDispatcher>();
        for (String s : pairs) {
            StringTokenizer st = new StringTokenizer(s, ":");
            String host = null;
            int port = -1;
            while (st.hasMoreTokens()) {
                host = st.nextToken();
                if (st.hasMoreTokens()) {
                    port = Integer.valueOf(st.nextToken());
                    displ.add(new UnicastCommandDispatcher(
                                  InetAddress.getByName(host),
                                  port));
                }
            }
        }
        return displ;
    }
}
