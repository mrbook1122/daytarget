package com.mrbook.daytarget;

import java.util.ArrayList;
import java.util.List;

public class Day {
    private int year;
    private int date;
    private int month;
    private List<Item> morning = new ArrayList<>();
    private List<Item> afternoon = new ArrayList<>();
    private List<Item> evening = new ArrayList<>();

    public String getDate() {
        return year + month + date + "";
    }

    public void setDate(int year, int month, int date) {
        this.year = year;
        this.month = month;
        this.date = date;
    }

    public List<Item> getMorning() {
        return morning;
    }


    public List<Item> getAfternoon() {
        return afternoon;
    }


    public List<Item> getEvening() {
        return evening;
    }

}
