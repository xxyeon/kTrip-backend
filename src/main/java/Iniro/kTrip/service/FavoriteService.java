package Iniro.kTrip.service;

import Iniro.kTrip.dao.FavoriteRepository;
import Iniro.kTrip.domain.Favorite;
import Iniro.kTrip.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;
    public Favorite craeteFavorite(String cid, int mid){
        return Favorite.builder()
                .mid(mid)
                .cid(cid)
                .build();
    }
    @Transactional
    public void addFavoriteSpot(String cid, int mid) throws IllegalAccessException {
          if(favoriteRepository.existsByMidAndCid(mid, cid)){
             throw new IllegalAccessException("이미 즐겨찾기되어있는 여행지입니다.");
          }
          else{
              favoriteRepository.save(craeteFavorite(cid, mid));
          }
    }
    @Transactional
    public void deleteFavoriteSpot(String cid, int mid) throws IllegalAccessException{
        if(!favoriteRepository.existsByMidAndCid(mid, cid)){
            throw new IllegalAccessException("즐겨찾기 정보가 없습니다.");
        }
        else{
            favoriteRepository.deleteFavoriteByMidAndCid(mid, cid);
        }
    }
}
