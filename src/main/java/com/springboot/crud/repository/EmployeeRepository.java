package com.springboot.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.springboot.crud.model.Employee;

@Service
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
