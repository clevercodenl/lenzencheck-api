package com.lenzencheckapi.LenzencheckAPI.Entities;

import jakarta.persistence.*;

import javax.annotation.Nullable;
import java.util.List;

@Entity
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Nullable
    private String ean;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
    private List<MerchantProductEntity> products;

    public ProductEntity() {
    }
    public ProductEntity(@Nullable String ean) {
        this.ean = ean;
    }
}
