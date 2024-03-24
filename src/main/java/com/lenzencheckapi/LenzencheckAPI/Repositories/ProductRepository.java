package com.lenzencheckapi.LenzencheckAPI.Repositories;

import com.lenzencheckapi.LenzencheckAPI.Entities.MerchantEntity;
import com.lenzencheckapi.LenzencheckAPI.Entities.ProductEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<ProductEntity, Long> {
    ProductEntity findByEan(String ean);
}