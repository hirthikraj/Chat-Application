package com.app.chat.repository;
import com.app.chat.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,Long> {
    Optional<User> findByUserName(String userName);
    Optional<User> findByUserId(String userId);
    void deleteByUserId(String userId);
}
