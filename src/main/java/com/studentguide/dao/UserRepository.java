package com.studentguide.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studentguide.entities.User;

public interface UserRepository extends JpaRepository<User,Integer> {

}
