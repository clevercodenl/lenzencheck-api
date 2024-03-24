package com.lenzencheckapi.LenzencheckAPI.Repositories;

import com.lenzencheckapi.LenzencheckAPI.Entities.BrandEntity;
import org.springframework.data.repository.CrudRepository;

public interface BrandRepository extends CrudRepository<BrandEntity, Integer> {
    BrandEntity findByName(String name);
}