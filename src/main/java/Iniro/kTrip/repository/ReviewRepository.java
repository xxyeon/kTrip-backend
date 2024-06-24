package Iniro.kTrip.repository;


import Iniro.kTrip.domain.Member;
import Iniro.kTrip.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer>
{

    List<Review> findByMember(Member member);
    void delete(Review review);


}
