package model.media;

public class Film extends Media {
    private String genre;

    public Film(String title, int year, String genre) {
        super(title, year);
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public void displayInfo() {
        System.out.println("Film: " + title + " (" + year + "), Genre: " + genre);
    }
}
