package org.am.fos.collector;

import org.am.fos.model.Dessert;
import org.am.fos.model.Drink;
import org.am.fos.model.BasicCourse;
import org.am.fos.model.OrderItem;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.am.fos.collector.OrderItemEnum.*;

public class ItemsCollector {
    private final int ITEM_TYPE_COLUMN_INDEX = 0;
    private final int ITEM_NAME_COLUMN_INDEX = 1;
    private final int ITEM_PRICE_COLUMN_INDEX = 2;
    private final int CUISINE_COLUMN_INDEX = 3;

    public List<OrderItem> loadItems(String fileName) {
        List<OrderItem> itemsList = new ArrayList<>();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStreamReader streamReader = new InputStreamReader(Objects.requireNonNull(classloader.getResourceAsStream(fileName)));
        try (BufferedReader br = new BufferedReader(streamReader)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineArr = line.split(",");
                OrderItem item = resolveObject(lineArr);
                if (Objects.nonNull(item)) {
                    itemsList.add(item);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return itemsList;
    }

    private OrderItem resolveObject(String[] lineArr) {
        int itemType;
        try {
            itemType = Integer.parseInt(lineArr[ITEM_TYPE_COLUMN_INDEX]);
        } catch (Exception ex) {
            System.out.println("Error while parsing ["+lineArr[ITEM_TYPE_COLUMN_INDEX]+"] to int.");
            return null;
        }
        String name = lineArr[ITEM_NAME_COLUMN_INDEX];
        if (name.trim().isEmpty()) {
            return null;
        }
        double price;
        try {
            price = Double.parseDouble(lineArr[ITEM_PRICE_COLUMN_INDEX]);
        } catch (Exception ex) {
            System.out.println("Error while parsing ["+lineArr[ITEM_PRICE_COLUMN_INDEX]+"] to double.");
            return null;
        }
        CuisineEnum cuisine = CuisineEnum.getByOrdinal(lineArr[CUISINE_COLUMN_INDEX]);
        if (cuisine == null) {
            System.out.println("Wrong cuisine ["+lineArr[CUISINE_COLUMN_INDEX]+"]. Cannot create object.");
            return null;
        }
        if (MAIN_COURSE.getOrdinal().equals(itemType)) {
            return new BasicCourse(name, price, cuisine);
        } else if (DESSERT.getOrdinal().equals(itemType)) {
            return new Dessert(name, price, cuisine);
        } else if (DRINK.getOrdinal().equals(itemType)) {
            return new Drink(name, price, cuisine);
        }
        System.out.println("Cannot create object frmm csv: ["+ Arrays.toString(lineArr) +"]");
        return null;
    }

}
