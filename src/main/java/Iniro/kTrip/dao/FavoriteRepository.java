package Iniro.kTrip.dao;

import Iniro.kTrip.domain.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    Boolean existsByMidAndCid(int mid, String cid);
    void deleteFavoriteByMidAndCid(int mid, String cid);
}
