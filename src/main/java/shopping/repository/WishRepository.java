package shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shopping.entity.Wish;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {
}
