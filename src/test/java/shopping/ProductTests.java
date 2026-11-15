package shopping;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class ProductTests {

    @DisplayName("상품 이름이 15자를 초과하면 예외가 발생한다")
    @Test
    void nameExceedsMaxLength() {
        String name = "a".repeat(16);
        int price = 0;
        String imageUrl = "https://api.dicebear.com/10.x/waves/svg";

        assertThatThrownBy(() -> Product.of(name, price, imageUrl))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
