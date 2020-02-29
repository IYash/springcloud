package com.clnn.elastic.domain;

import com.clnn.elastic.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserEntityRepository extends MongoRepository<UserEntity,String> {
}
