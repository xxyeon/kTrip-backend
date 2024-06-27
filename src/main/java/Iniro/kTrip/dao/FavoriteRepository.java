package Iniro.kTrip.dao;

import Iniro.kTrip.domain.Favorite;
import Iniro.kTrip.domain.Member;
import Iniro.kTrip.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    Boolean existsByMemberAndCid(Member member, String cid);
    void deleteFavoriteByMemberAndCid(Member member, String cid);
    List<Favorite> findByMember(Member member);
    List<Favorite> findCidByMember(Member member);
}
