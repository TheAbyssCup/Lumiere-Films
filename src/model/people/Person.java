package model.people;

public abstract class Person {
    protected String name;
    protected int year; // Join Year
    protected double dailyPay;

    public Person(String name, int year, double dailyPay) {
        this.name = name;
        this.year = year;
        this.dailyPay = dailyPay;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public double getDailyPay() {
        return dailyPay;
    }

    public void setDailyPay(double dailyPay) {
        this.dailyPay = dailyPay;
    }

    // Abstract method required by university project
    public abstract void displayInfo();
}
