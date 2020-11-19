package com.tutorial.repositories;

import org.springframework.data.annotation.Id;
import org.springframework.data.repository.CrudRepository;


public interface DocumentRepository extends CrudRepository<Id,Long> {
    
}
