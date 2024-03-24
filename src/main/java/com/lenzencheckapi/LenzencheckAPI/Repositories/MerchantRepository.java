package com.lenzencheckapi.LenzencheckAPI.Repositories;

import com.lenzencheckapi.LenzencheckAPI.Entities.MerchantEntity;
import org.springframework.data.repository.CrudRepository;

public interface MerchantRepository extends CrudRepository<MerchantEntity, Long> {
    MerchantEntity findByName(String name);
}