package com.lenzencheckapi.LenzencheckAPI.Entities;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.NotNull;
import java.util.List;

@Entity
@Table(name = "brands")
@DynamicUpdate
public class BrandEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String name;

    @NotNull
    private Integer productsCount = 0;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "brand", cascade = CascadeType.ALL)
    private List<MerchantProductEntity> products;

    public BrandEntity() {
    }

    public BrandEntity(@NotNull String name) {
        this.name = name;
        this.productsCount = 1;
    }

    public Integer getId() {
        return id;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public List<MerchantProductEntity> getProducts() {
        return products;
    }

    public Integer getProductsCount() {
        return productsCount;
    }

    public void setProductsCount(@NotNull Integer productsCount) {
        this.productsCount = productsCount;
    }
}
