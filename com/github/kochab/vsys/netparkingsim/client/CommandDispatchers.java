package com.github.kochab.vsys.netparkingsim.client;

import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Utility methods for command dispatchers.
 *
 * @author Fadi Moukayed
 * @author Matthias Siegmund
 *
 */

public final class CommandDispatchers {
    private CommandDispatchers() {}
    
    /**
     * For the given IP:PORT endpoints, construct command dispatchers that
     * will dispatch commands to given endpoints.
     *
     * @param pairs An iterable object containing host descriptors in the form of IP:PORT
     * @return Command dispatchers for the given endpoints
     */
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
