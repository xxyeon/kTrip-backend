package Iniro.kTrip.dao;

import Iniro.kTrip.domain.Favorite;
import Iniro.kTrip.domain.Member;
import Iniro.kTrip.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    Boolean existsByMidAndCid(int mid, String cid);
    void deleteFavoriteByMidAndCid(int mid, String cid);
    List<Favorite> findByMember(Member member);
}
