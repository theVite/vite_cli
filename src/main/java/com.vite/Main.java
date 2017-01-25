package com.vite;

import com.vite.cli.ViteCLI;
import com.vite.service.ViteClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main (String [] args) {
        new ViteCLI(System.in, System.out, new ViteClient());
    }
}
