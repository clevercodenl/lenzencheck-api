package com.lenzencheckapi.LenzencheckAPI.Entities;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

@Entity
@DynamicUpdate
@Table(name = "lens_types")
public class LensTypeEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String name;

    @NotNull
    private Integer productsCount = 0;

    @ManyToMany(cascade = CascadeType.REMOVE)
    private Set<MerchantProductEntity> merchantProducts;

    public LensTypeEntity() {
    }

    public LensTypeEntity(@NotNull String name) {
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

    public Set<MerchantProductEntity> getMerchantProducts() {
        return merchantProducts;
    }

    public Integer getProductsCount() {
        return productsCount;
    }

    public void setProductsCount(@NotNull Integer productsCount) {
        this.productsCount = productsCount;
    }
}
