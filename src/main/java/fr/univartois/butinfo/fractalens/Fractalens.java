package fr.univartois.butinfo.fractalens;

import picocli.CommandLine;

/**
 * Main class of the FractaLens application.
 */
public final class Fractalens {

    /**
     * Private constructor to prevent instantiation.
     */
    private Fractalens() {
        // Utility class
    }

    /**
     * Main method.
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        int exitCode = new CommandLine(new FractalensCommandCli()).execute(args);
        System.exit(exitCode);
    }
}
