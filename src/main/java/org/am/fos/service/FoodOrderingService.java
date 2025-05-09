package org.am.fos.service;

import org.am.fos.collector.CuisineEnum;
import org.am.fos.collector.ItemsCollector;
import org.am.fos.collector.OrderItemEnum;
import org.am.fos.model.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class FoodOrderingService {
    private static final InputCommands INPUT_COMMANDS = new InputCommands();

    private final Scanner scanner = new Scanner(System.in);
    private final ItemsCollector itemsCollector = new ItemsCollector();

    private String feedName;
    private List<BasicCourse> mainCourses;
    private List<Dessert> desserts;
    private List<Drink> drinks;

    public FoodOrderingService(String feedName) {
        this.feedName = feedName;
    }

    public String runOrder(Order order) {
        String choice = chooseCuisine(order);
        if (isExitCommandChoosen(choice)) {
            return choice;
        }
        setMenuItems(itemsCollector.loadItems(feedName), order.getCuisine());
        return selectMeals(order);
    }

    public String chooseCuisine(Order order) {
        String choice = null;
        boolean properChoice = false;
        while (!properChoice) {
            printCuisineOptions();
            choice = scanner.next();
            try {
                int choosenCuisine = Integer.parseInt(choice);
                if (choosenCuisine >= 0 && choosenCuisine < CuisineEnum.values().length) {
                    properChoice = true;
                    order.setCuisine(CuisineEnum.values()[choosenCuisine]);
                }
            } catch (NumberFormatException ex) {
                return choice;
            }
        }
        return choice;
    }

    public String selectMeals(Order order) {
        List<List<? extends OrderItem>> mealCategories = Arrays.asList(mainCourses, desserts, drinks);
        String choice = "";
        for (List<? extends OrderItem> category : mealCategories) {
            choice = askForSingleMeal(order, category);
            if (INPUT_COMMANDS.isExitOrResetCommand(choice)) {
                return choice;
            }
        }
        return choice;
    }

    public String summarizeOrder(Order order) {
        if (validateOrder(order)) {
            order.printSummary();
            return InputCommands.EXIT_COMMAND;
        } else {
            return InputCommands.RESTART_COMMAND;
        }
    }

    private <T> String askForSingleMeal(Order order, List<T> items) {
        String choice = null;
        boolean properChoice = false;
        while (!properChoice) {
            printMenuOptions(items);
            choice = scanner.next();
            try {
                int intOption = Integer.parseInt(choice);
                order.addMeal(items.get(intOption));
                properChoice = true;
            } catch (NumberFormatException ex) {
                if (isExitCommandChoosen(choice)) {
                    return choice;
                }
            }
        }
        return choice;
    }

    private void printCuisineOptions() {
        System.out.println("Choose your cuisine:");
        for (int i = 0; i < CuisineEnum.values().length; i++) {
            System.out.println("[" + i + "] - " + CuisineEnum.values()[i]);
        }
    }

    private <T> void printMenuOptions(List<T> items) {
        if (items.get(0) instanceof BasicCourse) {
            System.out.println("Select main course to order:");
        } else if (items.get(0) instanceof Dessert) {
            System.out.println("Select desert:");
        } else if (items.get(0) instanceof Drink) {
            System.out.println("Select drink:");
        }
        for (int i = 0; i < items.size(); i++) {
            System.out.println("[" + i + "] - " + items.get(i));
        }
        System.out.println("Press ["+InputCommands.SKIP_COMMAND.toUpperCase()+"] if you want to SKIP this dish.");
        System.out.println("Press ["+InputCommands.RESTART_COMMAND.toUpperCase()+"] if you want to RESTART order from the beginning.");
        System.out.println("Press ["+InputCommands.EXIT_COMMAND.toUpperCase()+"] if you want to EXIT without ordering meal.");
    }

    private boolean validateOrder(Order order) {
        return Objects.nonNull(order.getDrink()) || Objects.nonNull(order.getLunch())
                && (Objects.nonNull(order.getLunch().getBasicCourse()) || Objects.nonNull(order.getLunch().getDessert()));
    }

    private boolean isExitCommandChoosen(String choice) {
        if (INPUT_COMMANDS.isExitCommand(choice)) {
            System.out.println("Closing Food Ordering Service.");
        } else if (INPUT_COMMANDS.isSkipCommand(choice)) {
            System.out.println("Skipping current dish.");
        } else if (INPUT_COMMANDS.isResetCommand(choice)) {
            System.out.println("Restarting order.");
        }
        return INPUT_COMMANDS.isExitOrResetCommand(choice) || INPUT_COMMANDS.isSkipCommand(choice);
    }

    private void setMenuItems(List<OrderItem> menuItems, CuisineEnum cuisine) {
        mainCourses = menuItems.stream()
                .filter(e -> OrderItemEnum.MAIN_COURSE.equals(e.getType()))
                .filter(e -> cuisine.equals(e.getCuisine()))
                .map(e -> new BasicCourse(e.getName(), e.getPrice(), cuisine))
                .toList();
        desserts = menuItems.stream()
                .filter(e -> OrderItemEnum.DESSERT.equals(e.getType()))
                .filter(e -> cuisine.equals(e.getCuisine()))
                .map(e -> new Dessert(e.getName(), e.getPrice(), cuisine))
                .toList();
        drinks = menuItems.stream()
                .filter(e -> OrderItemEnum.DRINK.equals(e.getType()))
                .filter(e -> cuisine.equals(e.getCuisine()))
                .map(e -> new Drink(e.getName(), e.getPrice(), cuisine))
                .toList();
    }
}
