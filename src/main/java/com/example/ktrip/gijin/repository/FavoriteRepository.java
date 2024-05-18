package com.example.ktrip.gijin.repository;

import com.example.ktrip.gijin.domain.Favorite;
import com.example.ktrip.gijin.domain.Member;
import com.example.ktrip.gijin.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite,Integer>
{
    List<Favorite> findByMember(Member member);
    void delete(Favorite favorite);
}
