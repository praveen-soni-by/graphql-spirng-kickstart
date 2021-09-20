package com.syscho.graphql.company.resolver.query;

import com.syscho.graphql.company.domain.Department;
import com.syscho.graphql.company.domain.Employee;
import com.syscho.graphql.company.domain.Organization;
import com.syscho.graphql.company.repository.DepartmentRepository;
import com.syscho.graphql.exception.NoDataFoundException;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DataFetchingFieldSelectionSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DepartmentQuery implements GraphQLQueryResolver {

    private final DepartmentRepository repository;

    public Iterable<Department> departments(DataFetchingEnvironment environment) {
        DataFetchingFieldSelectionSet s = environment.getSelectionSet();
        if (s.contains("employees") && !s.contains("organization"))
            return repository.findAll(fetchEmployees());
        else if (!s.contains("employees") && s.contains("organization"))
            return repository.findAll(fetchOrganization());
        else if (s.contains("employees") && s.contains("organization"))
            return repository.findAll(fetchEmployees().and(fetchOrganization()));
        else
            return repository.findAll();
    }

    public Department department(Integer id, DataFetchingEnvironment environment) {
        Specification<Department> spec = byId(id);
        DataFetchingFieldSelectionSet selectionSet = environment.getSelectionSet();
        Set<Employee> employees = null;
        if (selectionSet.contains("employees")) {
            spec = spec.and(fetchEmployees());
        }
        if (selectionSet.contains("organization"))
            spec = spec.and(fetchOrganization());
        Department department = repository.findOne(spec).orElseThrow(() -> new NoDataFoundException(id));
        if (employees != null)
            department.setEmployees(employees);
        return department;
    }

    private Specification<Department> fetchOrganization() {
        return (root, query, builder) -> {
            Fetch<Department, Organization> f = root.fetch("organization", JoinType.LEFT);
            Join<Department, Organization> join = (Join<Department, Organization>) f;
            return join.getOn();
        };
    }

    private Specification<Department> fetchEmployees() {
        return (root, query, builder) -> {
            Fetch<Department, Employee> f = root.fetch("employees", JoinType.LEFT);
            Join<Department, Employee> join = (Join<Department, Employee>) f;
            return join.getOn();
        };
    }

    private Specification<Department> byId(Integer id) {
        return (root, query, builder) -> builder.equal(root.get("id"), id);
    }
}
