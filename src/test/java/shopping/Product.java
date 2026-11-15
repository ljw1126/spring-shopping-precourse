package shopping;

public class Product {
    private String name;
    private int price;
    private String imageUrl;

    private Product() {}

    private Product(String name, int price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static Product of(String name, int price, String imageUrl) {
        if(name.length() > 15) {
            throw new IllegalArgumentException("Product name is too long (limit 15)");
        }

        return new Product(name, price, imageUrl);
    }
}
