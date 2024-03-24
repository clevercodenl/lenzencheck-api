package com.lenzencheckapi.LenzencheckAPI.Services;

import com.lenzencheckapi.LenzencheckAPI.DataTransferObjects.FetchedProductDto;

import java.util.ArrayList;

public interface FetchProductsInterface {
     ArrayList<FetchedProductDto> fetchProducts();
}
