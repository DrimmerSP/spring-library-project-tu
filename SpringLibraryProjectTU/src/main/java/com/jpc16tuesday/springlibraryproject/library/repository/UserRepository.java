package com.jpc16tuesday.springlibraryproject.library.repository;

import com.jpc16tuesday.springlibraryproject.library.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository
      extends GenericRepository<User> {
}
