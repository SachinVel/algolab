package com.enpm613.algolab.repository;

import com.enpm613.algolab.entity.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
