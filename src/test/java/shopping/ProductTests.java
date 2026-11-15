package shopping;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class ProductTests {

    @DisplayName("")
    @Test
    void init() {
        Product product = new Product();
        assertThat(product).isNotNull();
    }

    public class Product {
    }

}
