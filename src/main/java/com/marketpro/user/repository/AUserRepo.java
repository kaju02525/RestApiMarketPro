package com.marketpro.user.repository;

import com.marketpro.user.model.authentication.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AUserRepo extends MongoRepository<User, String> {

    @Query("{mobile : ?0}")
    User findByMobile(String mobile);

    @Query("{email : ?0}")
    User findByEmail(String email);

    @Query("{last_name : ?0}")
    User findByLastName(String last_name);

}
