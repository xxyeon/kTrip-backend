package com.example.ktrip.gijin.repository;

import com.example.ktrip.gijin.domain.Member;
import com.example.ktrip.gijin.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer>
{

    List<Review> findByMember(Member member);
    void delete(Review review);


}
