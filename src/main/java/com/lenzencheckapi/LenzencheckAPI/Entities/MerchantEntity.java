package com.lenzencheckapi.LenzencheckAPI.Entities;

import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

@Entity
@Table(name = "merchants")
public class MerchantEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;

    @Nullable
    private String image_url;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "merchant", cascade = CascadeType.ALL)
    private List<MerchantProductEntity> products;

    public MerchantEntity() {
    }

    public MerchantEntity(@NotNull String name, @Nullable String image_url) {
        this.name = name;
        this.image_url = image_url;
    }


    public Long getId() {
        return id;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @Nullable
    public String getImage_url() {
        return image_url;
    }

    public List<MerchantProductEntity> getProducts() {
        return products;
    }
}
