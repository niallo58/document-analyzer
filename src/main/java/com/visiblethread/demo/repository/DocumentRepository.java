package com.visiblethread.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.visiblethread.demo.model.Document;
import com.visiblethread.demo.model.User;

@Repository
public interface DocumentRepository extends CrudRepository<Document, String> {
	
	@Query("from Document d where d.user in :users")
	List<Document> findByUsers(@Param("users") List<User> users);
	
	@Query("from Document d where d.user in :users and createdOn >= :createdAfter and createdOn <= :createdBefore")
	List<Document> findByUsersAndCreatedBetweenGivenDates(@Param("users") List<User> users, 
			@Param("createdAfter") LocalDateTime createdAfter, @Param("createdBefore") LocalDateTime createdBefore);

}
