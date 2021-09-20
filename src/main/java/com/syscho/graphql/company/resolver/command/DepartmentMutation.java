package com.syscho.graphql.company.resolver.command;

import com.syscho.graphql.company.domain.Department;
import com.syscho.graphql.company.domain.Organization;
import com.syscho.graphql.company.repository.DepartmentRepository;
import com.syscho.graphql.company.repository.OrganizationRepository;
import com.syscho.graphql.generated.types.DepartmentInput;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DepartmentMutation implements GraphQLMutationResolver {

    private final DepartmentRepository departmentRepository;
    private final OrganizationRepository organizationRepository;

    public Department newDepartment(DepartmentInput departmentInput) {
        Organization organization = organizationRepository.findById(departmentInput.getOrganizationId()).orElseThrow();
        return departmentRepository.save(new Department(null, departmentInput.getName(), null, organization));
    }

}
