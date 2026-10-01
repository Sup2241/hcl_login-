package com.hcl.VenueVista.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.VenueVista.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}