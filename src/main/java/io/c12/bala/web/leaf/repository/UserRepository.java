package io.c12.bala.web.leaf.repository;

import io.c12.bala.web.leaf.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, String> {

    UserEntity findUserEntityByEmailIgnoreCase(String email);

    boolean existsAllByEmailIgnoreCase(String email);
}
