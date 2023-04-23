package com.mc.repositores;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mc.dao.User;

public interface UserRepository extends JpaRepository<User, String>{

}
