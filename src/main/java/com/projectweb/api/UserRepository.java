package com.projectweb.api;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByFirstnameEquals(String uname);
}

