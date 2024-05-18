package com.example.ktrip.gijin.repository;

import com.example.ktrip.gijin.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Integer>
{
    Boolean existsById(String id);
    Member findById(String id);




}
