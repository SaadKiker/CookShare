package com.project.cookshare.repositories;

import com.project.cookshare.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String userName);
    User findUserById(Integer id);
}
