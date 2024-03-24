package com.lenzencheckapi.LenzencheckAPI.Repositories;

import com.lenzencheckapi.LenzencheckAPI.Entities.MerchantProductEntity;
import org.springframework.data.repository.CrudRepository;

public interface MerchantProductRepository extends CrudRepository<MerchantProductEntity, Long> {
}