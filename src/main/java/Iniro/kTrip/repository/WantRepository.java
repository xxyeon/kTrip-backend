package Iniro.kTrip.repository;



import Iniro.kTrip.domain.Member;
import Iniro.kTrip.domain.Want;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface WantRepository extends JpaRepository<Want,Integer>
{
    List<Want> findByMember(Member member);
    void delete(Want want);
}
