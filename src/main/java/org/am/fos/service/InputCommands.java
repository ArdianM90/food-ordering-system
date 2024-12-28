package org.am.fos.service;

public class InputCommands {
    public static final String EXIT_COMMAND = "x";
    public static final String RESTART_COMMAND = "r";
    public static final String SKIP_COMMAND = "s";

    public boolean isExitOrResetCommand(String command) {
        return command.equalsIgnoreCase(EXIT_COMMAND) || command.equalsIgnoreCase(RESTART_COMMAND);
    }

    public boolean isExitCommand(String command) {
        return command.equalsIgnoreCase(EXIT_COMMAND);
    }

    public boolean isResetCommand(String command) {
        return command.equalsIgnoreCase(RESTART_COMMAND);
    }

    public boolean isSkipCommand(String command) {
        return command.equalsIgnoreCase(SKIP_COMMAND);
    }


}
