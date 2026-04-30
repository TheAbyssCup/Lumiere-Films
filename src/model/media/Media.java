package model.media;

public abstract class Media {
    protected String title;
    protected int year;

    public Media(String title, int year) {
        this.title = title;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public abstract void displayInfo();
}
