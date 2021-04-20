package com.studentguide.dao;

import com.studentguide.entities.Outlet;

import org.springframework.data.repository.CrudRepository;

public interface OutletRepository extends CrudRepository<Outlet, Integer> {
    public Outlet findById(int id);

}
