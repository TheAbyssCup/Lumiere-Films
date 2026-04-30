package model.media;

public class Commercial extends Media {
    private String product;

    public Commercial(String title, int year, String product) {
        super(title, year);
        this.product = product;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Override
    public void displayInfo() {
        System.out.println("Commercial: " + title + " (" + year + "), Product: " + product);
    }
}
