package com.studentguide.dao;

import com.studentguide.entities.Hostel;

import org.springframework.data.repository.CrudRepository;

public interface HostelRepository extends CrudRepository<Hostel, Integer> {
    public Hostel findById(int id);

}
