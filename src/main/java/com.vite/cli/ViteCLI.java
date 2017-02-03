package com.vite.cli;

import com.vite.service.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Runs the Client
 */
public class ViteCLI implements CLI {
    private final BufferedReader in;
    private final PrintWriter out;
    private static final Logger LOGGER = LoggerFactory.getLogger(ViteCLI.class);
    private final Client client;
    private final Map<String, Function<String[], String>> commands = new HashMap<>();
    private final Map<String, String> commandDescriptions = new HashMap<>();
    private static final String EXIT_MESSAGE = "Closing the commandline interface...";
    private static final String HELP_MESSAGE = "Type 'help' for help";

    public ViteCLI(InputStream inputStream, OutputStream outputStream, Client client) {
        LOGGER.debug("Command line interface started...");
        in = new BufferedReader(new InputStreamReader(inputStream));
        out = new PrintWriter(new OutputStreamWriter(outputStream));
        this.client = client;
        commands.put("ping", this::ping);
        commandDescriptions.put("ping", "Pings the server");
        commands.put("quit", this::quit);
        commandDescriptions.put("quit", "Exits the cli");
        commands.put("help", this::help);
    }

    @Override
    public String ping(String ... args) {
        return client.pingServer();
    }

    @Override
    public String quit(String ... args) {
        try {
            in.close();
            out.close();
            client.close();
            return EXIT_MESSAGE;
        } catch (IOException ex) {
            LOGGER.error("Can't close streams", ex);
        } finally {
            System.exit(0);
        }
        return null;
    }

    @Override
    public String help (String ... args) {
        StringBuilder sb = new StringBuilder();

        if (args.length == 0) {
            List<String> sortedDescs = new ArrayList<>(commandDescriptions.keySet());
            Collections.sort(sortedDescs);
            sortedDescs.forEach(
                    key -> sb.append(formatHelpMessage(key))
            );

            return sb.toString();
        }

        List<String> sortedArgs = Arrays.asList(args);
        Collections.sort(sortedArgs);
        sortedArgs.forEach(
                arg -> sb.append(formatHelpMessage(arg))
        );

        return sb.toString();
    }

    /**
     * Formats the help message
     *
     * @param command Command to format
     * @return Help message for the command formatted
     */
     private String formatHelpMessage(String command) {
        StringBuilder sb = new StringBuilder();
        if (commandDescriptions.containsKey(command)) {
            sb.append(command);
            sb.append("\t");
            sb.append(commandDescriptions.get(command));
            sb.append("\n");
            return sb.toString();
        }

        sb.append("Command doesn't exist\n");
        sb.append(HELP_MESSAGE);

        return sb.toString();
    }

    @Override
    public void run() {
        String input;
        try {
            out.println("Type command when ready...");
            out.flush();
            while ((input = in.readLine()) != null) {
                final String[] command = input.trim().toLowerCase().split(" ");
                if (command.length == 0) {
                    continue;
                }
                final String commandName = command[0];
                final String[] commandArgs = Arrays.copyOfRange(command, 1, command.length);

                if (!commands.containsKey(commandName)) {
                    out.println("Unrecognized command: " + commandName);
                    out.println();
                    out.print(HELP_MESSAGE);
                    out.flush();
                    continue;
                }
                out.println(commands.get(commandName).apply(commandArgs));
                out.flush();
            }
        } catch (Exception ex) {
            LOGGER.error("Problem taking input", ex);
        }
    }
}
