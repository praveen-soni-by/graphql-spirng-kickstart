package com.syscho.graphql.company.resolver.query;

import com.syscho.graphql.company.domain.Employee;
import com.syscho.graphql.company.repository.EmployeeRepository;
import com.syscho.graphql.company.repository.filter.EmployeeFilter;
import com.syscho.graphql.company.repository.filter.FilterField;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class EmployeeQuery implements GraphQLQueryResolver {

    private final EmployeeRepository repository;

    private final JdbcTemplate jdbcTemplate;


    public List<Employee> employees(DataFetchingEnvironment environment) {
        log.info(environment.getSelectionSet().getImmediateFields().toString());
        return (List<Employee>) repository.findAll();
    }

    private String extractSelectedFields(DataFetchingEnvironment env) {
        return env.getSelectionSet().getImmediateFields().stream().map(selectedField ->
                selectedField.getName()).collect(Collectors.joining(","));
    }


    public Employee employee(Integer id, DataFetchingEnvironment env) {
        String query = "SELECT " + extractSelectedFields(env) + " FROM EMPLOYEE WHERE ID = " + id;
        return jdbcTemplate.queryForObject(query, Employee.class);

    }

    public Iterable<Employee> employeesWithFilter(EmployeeFilter filter) {
        Specification<Employee> spec = null;
        if (filter.getSalary() != null)
            spec = bySalary(filter.getSalary());
        if (filter.getAge() != null)
            spec = (spec == null ? byAge(filter.getAge()) : spec.and(byAge(filter.getAge())));
        if (filter.getPosition() != null)
            spec = (spec == null ? byPosition(filter.getPosition()) :
                    spec.and(byPosition(filter.getPosition())));
        if (spec != null)
            return repository.findAll(spec);
        else
            return repository.findAll();
    }

    private Specification<Employee> bySalary(FilterField filterField) {
        return (root, query, builder) -> filterField.generateCriteria(builder, root.get("salary"));
    }

    private Specification<Employee> byAge(FilterField filterField) {
        return (root, query, builder) -> filterField.generateCriteria(builder, root.get("age"));
    }

    private Specification<Employee> byPosition(FilterField filterField) {
        return (root, query, builder) -> filterField.generateCriteria(builder, root.get("position"));
    }
}
