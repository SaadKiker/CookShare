package com.project.cookshare.services.implementations;

import com.project.cookshare.models.Favorite;
import com.project.cookshare.repositories.FavoriteRepository;
import com.project.cookshare.services.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

//@Service
//public class FavoriteServiceImplentation implements FavoriteService {
//
//    private final FavoriteRepository favoriteRepository;
//
//    @Autowired
//    public FavoriteService(FavoriteRepository favoriteRepository) {
//        this.favoriteRepository = favoriteRepository;
//    }
//
//    @Override
//    public Favorite addFavorite(Favorite favorite) {
//        // Implementation
//    }
//
//    @Override
//    public Optional<Favorite> getFavoriteById(Integer id) {
//        // Implementation
//    }
//
//    @Override
//    public List<Favorite> getAllFavorites() {
//        // Implementation
//    }
//
//    @Override
//    public void deleteFavoriteById(Integer id) {
//        // Implementation
//    }
//}
