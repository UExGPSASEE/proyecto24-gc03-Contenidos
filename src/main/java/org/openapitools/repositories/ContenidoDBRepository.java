package org.openapitools.repositories;

import org.openapitools.modelDB.ContenidoDB;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContenidoDBRepository extends CrudRepository<ContenidoDB, Integer> {
    ContenidoDB findById(int id);
}