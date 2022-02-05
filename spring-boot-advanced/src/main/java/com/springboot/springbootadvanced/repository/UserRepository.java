package com.springboot.springbootadvanced.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.springboot.springbootadvanced.entity.User;

//public interface UserRepository extends CrudRepository<User, Long>{
@RepositoryRestResource(path="users" , collectionResourceRel="users")
public interface UserRepository extends PagingAndSortingRepository<User, Long>{
	
	List<User> findByRole(@Param("role") String role);

}
