package com.example.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User,Integer>{

	public User findByUsername(String name) ;
	public Iterable<User> findByCity(String city) ;
	
	@Query(value = "select id,username,password,roles from user where username=?1", nativeQuery = true)
	public List<Object[]> getAuthInfoByName(String name) ;
	
}
