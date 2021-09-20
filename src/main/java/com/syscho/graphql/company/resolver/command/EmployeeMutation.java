package com.syscho.graphql.company.resolver.command;

import com.syscho.graphql.company.domain.Department;
import com.syscho.graphql.company.domain.Employee;
import com.syscho.graphql.company.domain.Organization;
import com.syscho.graphql.company.repository.DepartmentRepository;
import com.syscho.graphql.company.repository.EmployeeRepository;
import com.syscho.graphql.company.repository.OrganizationRepository;
import com.syscho.graphql.generated.types.EmployeeInput;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EmployeeMutation implements GraphQLMutationResolver {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final OrganizationRepository organizationRepository;

    public Employee addEmployee(EmployeeInput employeeInput, DataFetchingEnvironment environment) {
        Department department = departmentRepository.findById(employeeInput.getDepartmentId()).orElseThrow();
        Organization organization = organizationRepository.findById(employeeInput.getOrganizationId()).orElseThrow();
        return employeeRepository.save(new Employee(null, employeeInput.getFirstName(), employeeInput.getLastName(),
                employeeInput.getPosition(), employeeInput.getAge(), employeeInput.getSalary(),
                department, organization));
    }

}
