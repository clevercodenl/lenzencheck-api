package com.lenzencheckapi.LenzencheckAPI.Tasks;

import com.lenzencheckapi.LenzencheckAPI.DataTransferObjects.FetchedProductDto;
import com.lenzencheckapi.LenzencheckAPI.Entities.*;
import com.lenzencheckapi.LenzencheckAPI.Repositories.*;
import com.lenzencheckapi.LenzencheckAPI.Services.FetchProductsInterface;
import com.lenzencheckapi.LenzencheckAPI.Services.GetLensTypesFromProductAttribute;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class ImportProductsTask {

    public static final String OTHER_BRANDS_NAME = "Andere Merken";
    private final FetchProductsInterface fetchProducts;
    private final MerchantProductRepository merchantProductRepository;
    private final MerchantRepository merchantRepository;
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;
    private final LensTypeRepository lensTypeRepository;
    private final GetLensTypesFromProductAttribute getLensTypesFromProductAttribute;
    private final LinkedHashMap<Integer, Integer> lensTypesCount = new LinkedHashMap<>();
    private final LinkedHashMap<Integer, Integer> brandProductsCount = new LinkedHashMap<>();

    @Autowired
    public ImportProductsTask(
            FetchProductsInterface fetchProducts,
            MerchantProductRepository merchantProductRepository,
            MerchantRepository merchantRepository,
            BrandRepository brandRepository,
            ProductRepository productRepository,
            GetLensTypesFromProductAttribute getLensTypesFromProductAttribute,
            LensTypeRepository lensTypeRepository
    ) {
        this.fetchProducts = fetchProducts;
        this.merchantProductRepository = merchantProductRepository;
        this.merchantRepository = merchantRepository;
        this.brandRepository = brandRepository;
        this.productRepository = productRepository;
        this.lensTypeRepository = lensTypeRepository;
        this.getLensTypesFromProductAttribute = getLensTypesFromProductAttribute;
    }

    @Scheduled(fixedDelay = 1000000)
    public void importProducts() {
        long start = System.currentTimeMillis();

        ArrayList<FetchedProductDto> productDtos = this.fetchProducts.fetchProducts();

        productDtos.forEach(productDto -> {
            MerchantEntity merchant = getMerchantEntity(productDto);
            BrandEntity brand = getBrandEntity(productDto);
            ProductEntity product = getProductEntity(productDto);
            HashSet<LensTypeEntity> lensTypeEntities = getLensTypeEntities(productDto);

            MerchantProductEntity merchantProduct = merchantProductRepository.save(new MerchantProductEntity(
                    productDto.name(),
                    productDto.description(),
                    productDto.awDeepLinkValue(),
                    productDto.awProductIdValue(),
                    productDto.awImageUrl(),
                    productDto.largeImage(),
                    productDto.alternateImage(),
                    productDto.alternateImage2(),
                    productDto.alternateImage3(),
                    productDto.alternateImage4(),
                    productDto.merchantProductIdValue(),
                    productDto.merchantDeepLink(),
                    productDto.specifications(),
                    productDto.productEan(),
                    productDto.currency(),
                    productDto.storePrice(),
                    productDto.searchPrice(),
                    productDto.displayPrice(),
                    productDto.deliveryCost(),
                    productDto.deliveryTime(),
                    productDto.stockQuantity(),
                    productDto.inStock(),
                    productDto.isForSale(),
                    productDto.upc(),
                    merchant,
                    brand,
                    product,
                    lensTypeEntities
            ));
            System.out.println(merchantProduct.getId() + " SAVED TO THE DATABASE");
        });

        Set<Integer> lensTypeIds = lensTypesCount.keySet();
        for (Integer lensTypeId : lensTypeIds) {
            System.out.println(lensTypeId + " -- "
                    + lensTypesCount.get(lensTypeId));
            Optional<LensTypeEntity> lensType = lensTypeRepository.findById(lensTypeId);

            lensType.ifPresent(lensTypeEntity -> {
                lensTypeEntity.setProductsCount(lensTypesCount.get(lensTypeId));
                lensTypeRepository.save(lensTypeEntity);
            });
        }

        Set<Integer> brandIds = brandProductsCount.keySet();
        for (Integer brandId : brandIds) {
            System.out.println(brandId + " -- "
                    + brandProductsCount.get(brandId));
            Optional<BrandEntity> brand = brandRepository.findById(brandId);

            brand.ifPresent(brandEntity -> {
                brandEntity.setProductsCount(brandProductsCount.get(brandId));
                brandRepository.save(brandEntity);
            });
        }
        System.out.println( "DONE SAVING TO THE DATABASE");

        long end = System.currentTimeMillis();
        float milliseconds = end - start;
        float seconds= milliseconds/1000F;
        float minutes=seconds/60F;

        System.out.println("How long did it take to import all products ?");
        System.out.println(seconds + " seconds");
        System.out.println(minutes + " minutes");
    }

    @NotNull
    private MerchantEntity getMerchantEntity(FetchedProductDto productDto) {
        String merchantName = productDto.merchantName();
        MerchantEntity merchant = merchantRepository.findByName(merchantName);
        if(merchant == null){
            merchant = new MerchantEntity(productDto.merchantId(), productDto.merchantImageUrl());
            merchantRepository.save(merchant);
        }
        return merchant;
    }

    @NotNull
    private HashSet<LensTypeEntity> getLensTypeEntities(FetchedProductDto productDto) {
        ArrayList<String> descriptionLensTypes = getLensTypesFromProductAttribute.getLensTypes(productDto.description());
        ArrayList<String> titleLensTypes = getLensTypesFromProductAttribute.getLensTypes(productDto.description());
        HashSet<String> uniqueLensTypes = new HashSet<>(descriptionLensTypes);
        uniqueLensTypes.addAll(titleLensTypes);

        HashSet<LensTypeEntity> lensTypeEntities = new HashSet<>();
        uniqueLensTypes.forEach(lensTypeName -> {
            LensTypeEntity lensTypeEntity = lensTypeRepository.findByName(lensTypeName);
            if(lensTypeEntity == null){
                LensTypeEntity newLensTypeEntity = new LensTypeEntity(lensTypeName);
                lensTypeRepository.save(newLensTypeEntity);
                lensTypeEntities.add(newLensTypeEntity);

                lensTypesCount.put(newLensTypeEntity.getId(), 1);
            } else {
                lensTypeEntities.add(lensTypeEntity);

                Integer currentLensTypeCount = lensTypesCount.get(lensTypeEntity.getId());
                lensTypesCount.put(lensTypeEntity.getId(), currentLensTypeCount +1);
            }
        });
        return lensTypeEntities;
    }

    @NotNull
    private ProductEntity getProductEntity(FetchedProductDto productDto) {
        String productEan = Objects.equals(productDto.productEan(), "") ? null : productDto.productEan();
        ProductEntity product;
        if(productEan == null){
            product = new ProductEntity();
            productRepository.save(product);
        } else {
            product = productRepository.findByEan(productEan);
            if(product == null){
                product = new ProductEntity(productEan);
                productRepository.save(product);
            }
        }
        return product;
    }

    @NotNull
    private BrandEntity getBrandEntity(FetchedProductDto productDto) {
        String brandName = Objects.equals(productDto.brandName(), "") ? OTHER_BRANDS_NAME : productDto.brandName();
        BrandEntity brand = brandRepository.findByName(brandName);

        if(brand == null){
            brand = new BrandEntity(brandName);
            brandRepository.save(brand);

            brandProductsCount.put(brand.getId(), 1);
        } else {
            Integer currentBrandProductsCount = brandProductsCount.get(brand.getId());
            brandProductsCount.put(brand.getId(), currentBrandProductsCount + 1);
        }
        return brand;
    }
}
