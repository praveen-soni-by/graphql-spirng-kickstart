package com.syscho.graphql.company.repository;

import org.springframework.data.repository.CrudRepository;
import com.syscho.graphql.company.domain.Organization;

public interface OrganizationRepository extends CrudRepository<Organization, Integer> {
}
