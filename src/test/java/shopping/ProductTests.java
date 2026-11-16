package shopping;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import shopping.entity.Product;

import static org.assertj.core.api.Assertions.*;

public class ProductTests {

    @DisplayName("상품 이름이 null이거나 비어 있거나 공백 뿐이면 예외가 발생한다")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void productNameIsBlank(String name) {
        int price = 0;
        String imageUrl = "https://api.dicebear.com/10.x/waves/svg";

        assertThatThrownBy(() -> Product.of(name, price, imageUrl))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품 이름이 정확히 15자이면 생성할 수 있다")
    @ParameterizedTest
    @ValueSource(strings = {"aaaaaaaaaaaaaaa", "가가가가가가가가가가가가가가가"})
    void nameWithMaxLengthIsAllowed(String name) {
        int price = 0;
        String imageUrl = "https://api.dicebear.com/10.x/waves/svg";

        assertThat(name).hasSize(15);
        assertThatCode(() -> Product.of(name, price, imageUrl))
                .doesNotThrowAnyException();
    }

    @DisplayName("상품 이름이 15자를 초과하면 예외가 발생한다")
    @Test
    void nameExceedsMaxLength() {
        String name = "a".repeat(16);
        int price = 0;
        String imageUrl = "https://api.dicebear.com/10.x/waves/svg";

        assertThatThrownBy(() -> Product.of(name, price, imageUrl))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품 이름에 한글, 영문, 숫자, 공백은 사용할 수 있다")
    @ParameterizedTest
    @ValueSource(strings = {"사과", "Apple", "123", "사과 Apple 123"})
    void nameContainsBasicCharacters(String name) {
        int price = 0;
        String imageUrl = "https://api.dicebear.com/10.x/waves/svg";

        assertThatCode(() -> Product.of(name, price, imageUrl))
                .doesNotThrowAnyException();
    }

    @DisplayName("상품 이름에 허용된 특수문자는 사용할 수 있다")
    @ParameterizedTest
    @ValueSource(strings = {"사과(빨강)", "상품 [1]", "A+B", "a-b", "A&B", "A/B", "a_b"})
    void nameContainsAllowedSpecialCharacter(String name) {
        int price = 0;
        String imageUrl = "https://api.dicebear.com/10.x/waves/svg";

        assertThatCode(() -> Product.of(name, price, imageUrl))
                .doesNotThrowAnyException();
    }

    @DisplayName("상품 이름에 허용되지 않은 특수문자가 있으면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"상품!", "상품@", "상품#", "상품*", "상품😀"})
    void nameContainsInvalidSpecialCharacter(String name) {
        int price = 0;
        String imageUrl = "https://api.dicebear.com/10.x/waves/svg";

        assertThatThrownBy(() -> Product.of(name, price, imageUrl))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
