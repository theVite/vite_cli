package com.vite.cli;

/**
 * Defines what the CLI should have
 */
interface CLI {
    /**
     * Pings the server
     *
     * @param args don't do anything for this function
     * @return Ping result
     */
    String ping(String ... args);

    /**
     * Exits the client
     *
     * @param args don't do anything for this function
     * @return Message stating client is closing
     */
    String quit(String ... args);

    /**
     * Prints out help messages for specified messages
     *
     * @param args Commands help is needed on
     * @return Help message
     */
    String help(String ... args);

    /**
     * Runs the Vite client
     */
    void run();
}
