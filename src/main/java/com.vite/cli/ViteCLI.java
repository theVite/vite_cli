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

/**
 * Runs the Client
 */
public class ViteCLI implements CLI {
    private final BufferedReader in;
    private final PrintWriter out;
    private static final Logger LOGGER = LoggerFactory.getLogger(ViteCLI.class);
    private final Client client;
    private final Map<String, Command> commands = new HashMap<>();
    private static final String EXIT_MESSAGE = "Closing the commandline interface...";
    private static final String HELP_MESSAGE = "Type 'help' for help";

    public ViteCLI(InputStream inputStream, OutputStream outputStream, Client client) {
        LOGGER.debug("Command line interface started...");
        in = new BufferedReader(new InputStreamReader(inputStream));
        out = new PrintWriter(new OutputStreamWriter(outputStream));
        this.client = client;
        commands.put(CommandName.PING.toString(), new Command(CommandName.PING, "Pings the server", this::ping));
        commands.put(CommandName.QUIT.toString(), new Command(CommandName.QUIT, "Exits the client", this::quit));
        commands.put(CommandName.EXIT.toString(), new Command(CommandName.EXIT, "Exits the client", this::quit));
        commands.put(CommandName.HELP.toString(), new Command(CommandName.HELP, "Displays help messages", this::help));
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
            List<String> sortedDescs = new ArrayList<>(commands.keySet());
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
        if (commands.containsKey(command)) {
            sb.append(command);
            sb.append("\t");
            sb.append(commands.get(command).getDescription());
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
                final CommandName commandName = CommandName.valueOf(command[0]);
                final String[] commandArgs = Arrays.copyOfRange(command, 1, command.length);

                if (!commands.containsKey(commandName)) {
                    out.println("Unrecognized command: " + commandName);
                    out.println();
                    out.print(HELP_MESSAGE);
                    out.flush();
                    continue;
                }
                out.println(commands.get(commandName).execute(commandArgs));
                out.flush();
            }
        } catch (Exception ex) {
            LOGGER.error("Problem taking input", ex);
        }
    }
}
