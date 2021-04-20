package com.studentguide.dao;

import com.studentguide.entities.Mess;

import org.springframework.data.repository.CrudRepository;

public interface MessRepository extends CrudRepository<Mess, Integer> {
    public Mess findById(int id);
}