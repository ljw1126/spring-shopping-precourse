package shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shopping.entity.Wish;

import java.util.List;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {

    boolean existsByMemberIdAndProductId(Long memberId, Long productId);

    List<Wish> findAllByMemberId(Long memberId);
}
