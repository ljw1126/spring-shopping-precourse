package shopping.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shopping.entity.Member;
import shopping.entity.PasswordEncoder;
import shopping.repository.MemberRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberServiceTests {

    private static final String EMAIL = "user@example.com";
    private static final String PASSWORD = "password123";
    private static final String ENCODED_PASSWORD = "encoded-password";

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberService memberService;

    @DisplayName("회원 가입하면 인코딩된 비밀번호로 회원을 저장한다")
    @Test
    void registerSavesMemberWithEncodedPassword() {
        given(passwordEncoder.encode(PASSWORD)).willReturn(ENCODED_PASSWORD);
        given(memberRepository.existsByEmail(EMAIL)).willReturn(false);
        given(memberRepository.save(any(Member.class))).willAnswer(invocation -> invocation.getArgument(0));

        Member member = memberService.register(EMAIL, PASSWORD);

        assertThat(member.getEmail()).isEqualTo(EMAIL);
        assertThat(member.getPassword()).isEqualTo(ENCODED_PASSWORD);
        verify(memberRepository).save(any(Member.class));
    }

    @DisplayName("이미 가입된 이메일이면 예외가 발생하고 저장하지 않는다")
    @Test
    void registerWithDuplicateEmail() {
        given(memberRepository.existsByEmail(EMAIL)).willReturn(true);

        assertThatThrownBy(() -> memberService.register(EMAIL, PASSWORD))
                .isInstanceOf(IllegalArgumentException.class);
        verify(memberRepository, never()).save(any());
    }

    @DisplayName("대소문자만 다른 이메일도 이미 가입된 이메일로 판단한다")
    @Test
    void registerWithDuplicateEmailIgnoringCase() {
        given(memberRepository.existsByEmail(EMAIL)).willReturn(true);

        assertThatThrownBy(() -> memberService.register("USER@EXAMPLE.COM", PASSWORD))
                .isInstanceOf(IllegalArgumentException.class);
        verify(memberRepository, never()).save(any());
    }
}
