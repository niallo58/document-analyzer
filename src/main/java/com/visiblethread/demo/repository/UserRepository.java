package com.visiblethread.demo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.visiblethread.demo.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	Optional<User> findByEmailAddressIgnoreCase(@Param("emailAddress") String emailAddress);
	
}
