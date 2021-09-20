package com.syscho.graphql.company.resolver.command;


import com.syscho.graphql.company.domain.Organization;
import com.syscho.graphql.company.repository.OrganizationRepository;
import com.syscho.graphql.generated.types.OrganizationInput;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrganizationMutation implements GraphQLMutationResolver {

    private final OrganizationRepository repository;

    public Organization newOrganization(OrganizationInput organizationInput) {
        return repository.save(new Organization(null, organizationInput.getName(), null, null));
    }

}
