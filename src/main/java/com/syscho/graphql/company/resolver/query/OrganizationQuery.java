package com.syscho.graphql.company.resolver.query;

import com.syscho.graphql.company.domain.Organization;
import com.syscho.graphql.company.repository.OrganizationRepository;
import com.syscho.graphql.exception.NoDataFoundException;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrganizationQuery implements GraphQLQueryResolver {

    private final OrganizationRepository repository;

    public Iterable<Organization> organizations() {
        return repository.findAll();
    }

    public Organization organization(Integer id) {
        return repository.findById(id).orElseThrow(() -> new NoDataFoundException(String.valueOf(id)));
    }
}
