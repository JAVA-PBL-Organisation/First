package com.studentguide.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.studentguide.entities.Favorite;
import com.studentguide.entities.User;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {

	// pagination
	@Query("from Favorite as c where c.user.id=:userId")
	public Page<Favorite> findFavoriteByUser(@Param("userId") int userId, Pageable pageable);

	public List<Favorite> findByNameContainingAndUser(String name, User user);
}
