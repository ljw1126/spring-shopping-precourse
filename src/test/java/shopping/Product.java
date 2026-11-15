package shopping;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Product {
    private static final Pattern INVALID_CHAR = Pattern.compile("[^가-힣a-zA-Z0-9 ()\\[\\]+\\-&/_]");

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

        Matcher matcher = INVALID_CHAR.matcher(name);
        if(matcher.find()) {
            throw new IllegalArgumentException("Product name included invalid characters : " + matcher.group());
        }

        return new Product(name, price, imageUrl);
    }
}
