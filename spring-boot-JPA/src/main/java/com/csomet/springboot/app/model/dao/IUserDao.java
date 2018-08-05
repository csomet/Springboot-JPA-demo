package com.csomet.springboot.app.model.dao;

import org.springframework.data.repository.CrudRepository;
import com.csomet.springboot.app.model.entity.User;

public interface IUserDao extends CrudRepository<User, Long> {

	public User findByUsername(String username);
}
