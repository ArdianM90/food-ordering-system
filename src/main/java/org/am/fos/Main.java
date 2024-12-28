package org.am.fos;

import org.am.fos.model.Order;
import org.am.fos.service.FoodOrderingService;
import org.am.fos.service.InputCommands;

public class Main {
    private static final InputCommands INPUT_COMMANDS = new InputCommands();

    public static void main(String[] args) {
        String feedName = "feed.csv";
        FoodOrderingService fos = new FoodOrderingService(feedName);
        String choice = InputCommands.RESTART_COMMAND;
        Order order;
        while (!INPUT_COMMANDS.isExitCommand(choice)) {
            if (INPUT_COMMANDS.isResetCommand(choice)) {
                order = new Order();
                choice = fos.runOrder(order);
                if (!INPUT_COMMANDS.isExitOrResetCommand(choice)) {
                    choice = fos.summarizeOrder(order);
                }
            }
        }
    }
}