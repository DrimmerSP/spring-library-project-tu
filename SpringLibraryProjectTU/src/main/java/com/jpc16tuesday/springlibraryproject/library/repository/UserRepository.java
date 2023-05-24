package com.jpc16tuesday.springlibraryproject.library.repository;

import com.jpc16tuesday.springlibraryproject.library.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository
        extends GenericRepository<User> {

    //select * from users where login = ?
//    @Query(nativeQuery = true,
//            value = "select * from users where login = :login")
    User findUserByLogin(String login);

    User findUserByEmail(String email);

//    User getUserByPhone(String phone);
}
