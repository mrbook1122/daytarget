package com.mrbook.daytarget;

import java.util.ArrayList;
import java.util.List;

public class Day {
    private int year;
    private int date;
    private int month;
    public String name;
    private List<Item> morning = new ArrayList<>();
    private List<Item> afternoon = new ArrayList<>();
    private List<Item> evening = new ArrayList<>();

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public List<Item> getMorning() {
        return morning;
    }

    public void setMorning(List<Item> morning) {
        this.morning = morning;
    }

    public List<Item> getAfternoon() {
        return afternoon;
    }

    public void setAfternoon(List<Item> afternoon) {
        this.afternoon = afternoon;
    }

    public List<Item> getEvening() {
        return evening;
    }

    public void setEvening(List<Item> evening) {
        this.evening = evening;
    }
}
