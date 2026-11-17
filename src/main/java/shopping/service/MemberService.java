package shopping.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.entity.Member;
import shopping.entity.PasswordEncoder;
import shopping.repository.MemberRepository;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member register(String email, String password) {
        if (memberRepository.existsByEmail(email.toLowerCase(Locale.ROOT))) {
            throw new  IllegalArgumentException("Email already exists");
        }

        Member member = Member.of(email, password, passwordEncoder);
        memberRepository.save(member);
        return member;
    }
}
