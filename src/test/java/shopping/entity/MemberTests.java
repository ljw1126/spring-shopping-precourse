package shopping.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MemberTests {

    private static final String VALID_EMAIL = "user@example.com";
    private static final String VALID_PASSWORD = "password123";

    private final PasswordEncoder passwordEncoder = new FakePasswordEncoder();

    @DisplayName("이메일이 null이거나 비어 있거나 공백 뿐이면 예외가 발생한다")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void emailIsBlank(String email) {
        assertThatThrownBy(() -> Member.of(email, VALID_PASSWORD, passwordEncoder))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이메일 형식이 올바르지 않으면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"plainaddress", "@example.com", "user@", "user@@example.com"})
    void emailFormatIsInvalid(String email) {
        assertThatThrownBy(() -> Member.of(email, VALID_PASSWORD, passwordEncoder))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("올바른 이메일 형식이면 회원을 생성할 수 있다")
    @Test
    void validEmail() {
        assertThatCode(() -> Member.of(VALID_EMAIL, VALID_PASSWORD, passwordEncoder))
                .doesNotThrowAnyException();
    }

    @DisplayName("이메일은 대소문자를 구분하지 않으므로 소문자로 저장한다")
    @Test
    void emailIsCaseInsensitive() {
        Member member = Member.of("User@Example.COM", VALID_PASSWORD, passwordEncoder);

        assertThat(member.getEmail()).isEqualTo("user@example.com");
    }

    @DisplayName("비밀번호가 null이거나 비어 있거나 공백 뿐이면 예외가 발생한다")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "        "})
    void passwordIsBlank(String password) {
        assertThatThrownBy(() -> Member.of(VALID_EMAIL, password, passwordEncoder))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("비밀번호가 8자 미만이면 예외가 발생한다")
    @Test
    void passwordIsTooShort() {
        String password = "a".repeat(7);

        assertThatThrownBy(() -> Member.of(VALID_EMAIL, password, passwordEncoder))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("비밀번호가 정확히 8자이면 회원을 생성할 수 있다")
    @Test
    void passwordWithMinLengthIsAllowed() {
        String password = "a".repeat(8);

        assertThatCode(() -> Member.of(VALID_EMAIL, password, passwordEncoder))
                .doesNotThrowAnyException();
    }

    @DisplayName("비밀번호는 원문이 아닌 인코딩된 값으로 저장한다")
    @Test
    void passwordIsStoredEncoded() {
        Member member = Member.of(VALID_EMAIL, VALID_PASSWORD, passwordEncoder);

        assertThat(member.getPassword())
                .isEqualTo(passwordEncoder.encode(VALID_PASSWORD))
                .isNotEqualTo(VALID_PASSWORD);
    }

    private static class FakePasswordEncoder implements PasswordEncoder {

        private static final String PREFIX = "encoded:";

        @Override
        public String encode(CharSequence rawPassword) {
            return PREFIX + rawPassword;
        }

        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return encode(rawPassword).equals(encodedPassword);
        }
    }
}
