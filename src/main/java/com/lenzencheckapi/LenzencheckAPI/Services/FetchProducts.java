package com.lenzencheckapi.LenzencheckAPI.Services;

import com.lenzencheckapi.LenzencheckAPI.DataTransferObjects.FetchedProductDto;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;

@Service
final public class FetchProducts implements FetchProductsInterface {
    final private static String IMPORT_ENDPOINT = "https://productdata.awin.com/datafeed/download/apikey/c66c314e74498fdb88272e83a36a8859/language/nl/fid/76533,85148/columns/aw_deep_link,product_name,aw_product_id,merchant_product_id,merchant_image_url,description,merchant_category,search_price,merchant_name,merchant_id,category_name,category_id,aw_image_url,currency,store_price,delivery_cost,merchant_deep_link,language,last_updated,display_price,data_feed_id,brand_name,brand_id,colour,product_short_description,specifications,condition,product_model,model_number,dimensions,keywords,promotional_text,product_type,commission_group,merchant_product_category_path,merchant_product_second_category,merchant_product_third_category,rrp_price,saving,savings_percent,base_price,base_price_amount,base_price_text,product_price_old,delivery_restrictions,delivery_weight,warranty,terms_of_contract,delivery_time,in_stock,stock_quantity,valid_from,valid_to,is_for_sale,web_offer,pre_order,stock_status,size_stock_status,size_stock_amount,merchant_thumb_url,large_image,alternate_image,aw_thumb_url,alternate_image_two,alternate_image_three,alternate_image_four,reviews,average_rating,rating,number_available,ean,isbn,upc,mpn,parent_product_id,product_GTIN,basket_link/format/csv/delimiter/%2C/compression/gzip/adultcontent/1/";


     public ArrayList<FetchedProductDto> fetchProducts(){
         RestTemplate restTemplate = new RestTemplate();

        //TODO: if request is not 200, log to sentry
         byte[] response = restTemplate.getForObject(IMPORT_ENDPOINT, byte[].class);

         if (response != null && response.length > 0) {
             String csvContent = extractCSVContent(response);
             return parseCSVContent(csvContent);
         } else {
             System.out.println("Failed to download the CSV file from the API.");
         }

         return new ArrayList<>();
    }

    private String extractCSVContent(byte[] compressedData) {
        try (GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(compressedData))) {
            // Read the content from the GZIP input stream
            StringBuilder contentBuilder = new StringBuilder();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = gzipInputStream.read(buffer)) != -1) {
                contentBuilder.append(new String(buffer, 0, bytesRead, StandardCharsets.UTF_8));
            }

            // Convert the content to a string
            return contentBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to extract CSV content: " + e.getMessage());
            return null;
        }
    }

    private ArrayList<FetchedProductDto> parseCSVContent(String csvContent){
        ArrayList<FetchedProductDto> importProductDtos = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(new ByteArrayInputStream(csvContent.getBytes())))) {
            String[] headers = csvReader.readNext(); // Assuming the first row contains headers
//            System.out.println(Arrays.toString(headers));

            if (headers != null) {
                String[] nextLine;
                while (true) {
                    try {
                        nextLine = csvReader.readNext();
                        if (nextLine == null) {
                            break; // End of file
                        }

                        // Process each row here
                        importProductDtos.add(processCSVRow(headers, nextLine));
                    } catch (CsvValidationException e) {
                        // Handle CsvValidationException (e.g., log the error, skip the record, or terminate the process)
                        System.err.println("CsvValidationException: " + e.getMessage());
                    }
                }
            }

            return importProductDtos;
        } catch (IOException|CsvValidationException e) {
            e.printStackTrace();
            System.out.println("Failed to parse CSV content: " + e.getMessage());
        }

        return importProductDtos;
    }
    private FetchedProductDto processCSVRow(String[] headers, String[] row) {
        String awDeepLinkValue = getValueFromCSVRow(headers, row, AwinDataFeedKeys.AW_DEEP_LINK);
        String awProductIdValue = getValueFromCSVRow(headers, row, AwinDataFeedKeys.AW_PRODUCT_ID);

        String awImageUrl = getValueFromCSVRow(headers, row, AwinDataFeedKeys.AW_IMAGE_URL);
        String merchantImageUrl = getValueFromCSVRow(headers, row, AwinDataFeedKeys.MERCHANT_IMAGE_URL);
        String largeImage = getValueFromCSVRow(headers, row, AwinDataFeedKeys.LARGE_IMAGE);
        String alternateImage = getValueFromCSVRow(headers, row, AwinDataFeedKeys.ALTERNATE_IMAGE);
        String alternateImage2 = getValueFromCSVRow(headers, row, AwinDataFeedKeys.ALTERNATE_IMAGE_TWO);
        String alternateImage3 = getValueFromCSVRow(headers, row, AwinDataFeedKeys.ALTERNATE_IMAGE_THREE);
        String alternateImage4 = getValueFromCSVRow(headers, row, AwinDataFeedKeys.ALTERNATE_IMAGE_FOUR);

        String merchantId = getValueFromCSVRow(headers, row, AwinDataFeedKeys.MERCHANT_NAME);
        String merchantName = getValueFromCSVRow(headers, row, AwinDataFeedKeys.MERCHANT_NAME);
        String merchantProductIdValue = getValueFromCSVRow(headers, row, AwinDataFeedKeys.MERCHANT_PRODUCT_ID);
        String merchantDeepLink = getValueFromCSVRow(headers, row, AwinDataFeedKeys.MERCHANT_DEEP_LINK);

        String brandId = getValueFromCSVRow(headers, row, AwinDataFeedKeys.BRAND_ID);
        String brandName = getValueFromCSVRow(headers, row, AwinDataFeedKeys.BRAND_NAME);

        String specifications = getValueFromCSVRow(headers, row, AwinDataFeedKeys.SPECIFICATIONS);
        String name = getValueFromCSVRow(headers, row, AwinDataFeedKeys.PRODUCT_NAME);
        String productEan = getValueFromCSVRow(headers, row, AwinDataFeedKeys.EAN);
        String description = getValueFromCSVRow(headers, row, AwinDataFeedKeys.DESCRIPTION);
        String currency = getValueFromCSVRow(headers, row, AwinDataFeedKeys.CURRENCY);
        String storePrice = getValueFromCSVRow(headers, row, AwinDataFeedKeys.STORE_PRICE);
        String searchPrice = getValueFromCSVRow(headers, row, AwinDataFeedKeys.SEARCH_PRICE);
        String displayPrice = getValueFromCSVRow(headers, row, AwinDataFeedKeys.DISPLAY_PRICE);
        String deliveryCost = getValueFromCSVRow(headers, row, AwinDataFeedKeys.DELIVERY_COST);
        String deliveryTime = getValueFromCSVRow(headers, row, AwinDataFeedKeys.DELIVERY_TIME);
        String stockQuantity = getValueFromCSVRow(headers, row, AwinDataFeedKeys.STOCK_QUANTITY);
        String inStock = getValueFromCSVRow(headers, row, AwinDataFeedKeys.IN_STOCK);
        String isForSale = getValueFromCSVRow(headers, row, AwinDataFeedKeys.IS_FOR_SALE);
        String upc = getValueFromCSVRow(headers, row, AwinDataFeedKeys.UPC);

        return new FetchedProductDto(
                awDeepLinkValue,
                awProductIdValue,
                awImageUrl,
                merchantImageUrl,
                largeImage,
                alternateImage,
                alternateImage2,
                alternateImage3,
                alternateImage4,
                merchantId,
                merchantName,
                merchantProductIdValue,
                merchantDeepLink,
                brandId,
                brandName,
                specifications,
                name,
                productEan,
                description,
                currency,
                storePrice,
                searchPrice,
                displayPrice,
                deliveryCost,
                deliveryTime,
                stockQuantity,
                inStock,
                isForSale,
                upc
        );
    }

    private String getValueFromCSVRow(String[] headers, String[] row, String key){
        return row[Arrays.asList(headers).indexOf(key)];
    }
}
