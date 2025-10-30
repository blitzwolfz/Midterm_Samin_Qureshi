package com.gbc.comp3074.midterm_samin_qureshi;

import java.util.ArrayList;

public class DataManager {
    private static ArrayList<Integer> history = new ArrayList<>();

    public static void addToHistory(int number) {
        // Add only if not already in history
        if (!history.contains(number)) {
            history.add(number);
        }
    }

    public static ArrayList<Integer> getHistory() {
        return history;
    }

    public static void clearHistory() {
        history.clear();
    }
}
