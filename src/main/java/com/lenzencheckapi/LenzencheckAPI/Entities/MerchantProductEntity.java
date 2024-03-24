package com.lenzencheckapi.LenzencheckAPI.Entities;

import jakarta.persistence.*;
import javax.annotation.Nullable;
import java.util.Set;

@Entity
@Table(name = "merchant_products")
public class MerchantProductEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Nullable
    private String awDeepLinkValue;
    @Nullable
    private String awProductIdValue;
    @Nullable
    private String awImageUrl;
    @Nullable
    private String largeImage;
    @Nullable
    private String alternateImage;
    @Nullable
    private String alternateImage2;
    @Nullable
    private String alternateImage3;
    @Nullable
    private String alternateImage4;
    @Nullable
    private String merchantProductIdValue;
    @Nullable
    private String merchantDeepLink;
    @Nullable
    private String specifications;
    @Nullable
    private String productEan;
    @Nullable
    private String currency;
    @Nullable
    private String storePrice;
    @Nullable
    private String searchPrice;
    @Nullable
    private String displayPrice;
    @Nullable
    private String deliveryCost;
    @Nullable
    private String deliveryTime;
    @Nullable
    private String stockQuantity;
    @Nullable
    private String inStock;
    @Nullable
    private String isForSale;
    @Nullable
    private String upc;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "merchant_id", foreignKey=@ForeignKey(name = "FK_merchants"), nullable = false)
    private MerchantEntity merchant;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "brand_id", foreignKey=@ForeignKey(name = "FK_brands"))
    private BrandEntity brand;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "product_id", foreignKey=@ForeignKey(name = "FK_products"), nullable = false)
    private ProductEntity product;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "merchant_product_lens_types",
            joinColumns = @JoinColumn(name = "merchant_product_id"),
            inverseJoinColumns = @JoinColumn(name = "lens_type_id"))
    private Set<LensTypeEntity> lensTypes;


    protected MerchantProductEntity() {}
    public MerchantProductEntity(String name, String description, @Nullable String awDeepLinkValue, @Nullable String awProductIdValue, @Nullable String awImageUrl, @Nullable String largeImage, @Nullable String alternateImage, @Nullable String alternateImage2, @Nullable String alternateImage3, @Nullable String alternateImage4, @Nullable String merchantProductIdValue, @Nullable String merchantDeepLink, @Nullable String specifications, @Nullable String productEan, @Nullable String currency, @Nullable String storePrice, @Nullable String searchPrice, @Nullable String displayPrice, @Nullable String deliveryCost, @Nullable String deliveryTime, @Nullable String stockQuantity, @Nullable String inStock, @Nullable String isForSale, @Nullable String upc, MerchantEntity merchant, @Nullable BrandEntity brand, ProductEntity product, Set<LensTypeEntity> lensTypes) {
        this.name = name;
        this.description = description;
        this.awDeepLinkValue = awDeepLinkValue;
        this.awProductIdValue = awProductIdValue;
        this.awImageUrl = awImageUrl;
        this.largeImage = largeImage;
        this.alternateImage = alternateImage;
        this.alternateImage2 = alternateImage2;
        this.alternateImage3 = alternateImage3;
        this.alternateImage4 = alternateImage4;
        this.merchantProductIdValue = merchantProductIdValue;
        this.merchantDeepLink = merchantDeepLink;
        this.specifications = specifications;
        this.productEan = productEan;
        this.currency = currency;
        this.storePrice = storePrice;
        this.searchPrice = searchPrice;
        this.displayPrice = displayPrice;
        this.deliveryCost = deliveryCost;
        this.deliveryTime = deliveryTime;
        this.stockQuantity = stockQuantity;
        this.inStock = inStock;
        this.isForSale = isForSale;
        this.upc = upc;
        this.merchant = merchant;
        this.brand = brand;
        this.product = product;
        this.lensTypes = lensTypes;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAwDeepLinkValue() {
        return awDeepLinkValue;
    }

    public String getAwProductIdValue() {
        return awProductIdValue;
    }

    public String getAwImageUrl() {
        return awImageUrl;
    }

    public String getLargeImage() {
        return largeImage;
    }

    public String getAlternateImage() {
        return alternateImage;
    }

    public String getAlternateImage2() {
        return alternateImage2;
    }

    public String getAlternateImage3() {
        return alternateImage3;
    }

    public String getAlternateImage4() {
        return alternateImage4;
    }

    public String getMerchantProductIdValue() {
        return merchantProductIdValue;
    }

    public String getMerchantDeepLink() {
        return merchantDeepLink;
    }

    public String getSpecifications() {
        return specifications;
    }

    public String getProductEan() {
        return productEan;
    }

    public String getCurrency() {
        return currency;
    }

    public String getStorePrice() {
        return storePrice;
    }

    public String getSearchPrice() {
        return searchPrice;
    }

    public String getDisplayPrice() {
        return displayPrice;
    }

    public String getDeliveryCost() {
        return deliveryCost;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public String getStockQuantity() {
        return stockQuantity;
    }

    public String getInStock() {
        return inStock;
    }

    public String getIsForSale() {
        return isForSale;
    }

    public String getUpc() {
        return upc;
    }
    public MerchantEntity getMerchant() {
        return merchant;
    }

    public BrandEntity getBrand() {
        return brand;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public Set<LensTypeEntity> getLensTypes() {
        return lensTypes;
    }
}
