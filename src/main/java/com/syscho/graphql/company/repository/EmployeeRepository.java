package com.syscho.graphql.company.repository;

import com.syscho.graphql.company.domain.Employee;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Integer>,
		JpaSpecificationExecutor<Employee> {
}
