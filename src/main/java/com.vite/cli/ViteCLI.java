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

public class ViteCLI {
    private final BufferedReader in;
    private final PrintWriter out;
    private static final Logger LOGGER = LoggerFactory.getLogger(ViteCLI.class);
    private final Client client;

    public ViteCLI(InputStream inputStream, OutputStream outputStream, Client client) {
        LOGGER.debug("Command line interface started...");
        in = new BufferedReader(new InputStreamReader(inputStream));
        out = new PrintWriter(new OutputStreamWriter(outputStream));
        this.client = client;
    }

    public void run() {
        for (;;) {
            out.println("What would you like to do?\n\t1) Ping Server \n\t9) Exit");
            out.flush();
            Integer choice = 0;
            try {
                choice = Integer.parseInt(in.readLine());
            } catch (NumberFormatException ex) {
                LOGGER.error("Not a number", ex);
            } catch (IOException ex) {
                LOGGER.error("Invalid input", ex);
            }

            switch (choice) {
                case 1:
                    out.println("Pinging Server...");
                    out.flush();
                    out.println(client.pingServer());
                    out.flush();
                    break;
                case 9:
                    out.println("Closing the commandline interface...");
                    out.flush();
                    try {
                        in.close();
                        out.close();
                        client.close();
                    } catch (IOException ex) {
                        LOGGER.error("Can't close streams", ex);
                    } finally {
                        System.exit(0);
                    }

                    break;
                default:
                    out.println("Invalid Choice");
                    out.flush();
            }
        }

    }
}
