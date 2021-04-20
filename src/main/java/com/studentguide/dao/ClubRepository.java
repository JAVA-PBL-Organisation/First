package com.studentguide.dao;

import com.studentguide.entities.Club;

import org.springframework.data.repository.CrudRepository;

public interface ClubRepository extends CrudRepository<Club, Integer> {
    public Club findById(int id);
}
