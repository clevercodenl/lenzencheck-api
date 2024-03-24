package com.lenzencheckapi.LenzencheckAPI.Repositories;

import com.lenzencheckapi.LenzencheckAPI.Entities.LensTypeEntity;
import com.lenzencheckapi.LenzencheckAPI.Entities.ProductEntity;
import org.springframework.data.repository.CrudRepository;

public interface LensTypeRepository extends CrudRepository<LensTypeEntity, Integer> {
    LensTypeEntity findByName(String name);
}