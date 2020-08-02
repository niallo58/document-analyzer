package com.visiblethread.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.visiblethread.demo.model.UserGroupLink;

@Repository
public interface UserGroupLinkRepository extends JpaRepository<UserGroupLink, String> {
	
	@Query("from UserGroupLink ugl join ugl.user u where upper(u.id)=upper(:userId)")
	List<UserGroupLink> findByUserId(@Param("userId") String userId);
	
	@Query("from UserGroupLink ugl join ugl.group g where upper(g.groupName)=upper(:groupName)")
	List<UserGroupLink> findByGroupName(@Param("groupName") String groupName);
	
	@Query("from UserGroupLink ugl join ugl.user u where u.createdOn <= :beforeDate")
	List<UserGroupLink> findByUserCreatedOnBefore(@Param("beforeDate") LocalDateTime beforeDate);

}
