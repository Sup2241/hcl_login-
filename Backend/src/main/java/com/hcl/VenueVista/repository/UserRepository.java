package com.hcl.VenueVista.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.VenueVista.model.User;

@Repository 
public interface UserRepository extends JpaRepository<User, Long> {

}