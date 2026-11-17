package shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);
}
