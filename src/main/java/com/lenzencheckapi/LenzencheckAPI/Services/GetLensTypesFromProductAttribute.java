package com.lenzencheckapi.LenzencheckAPI.Services;

import com.lenzencheckapi.LenzencheckAPI.DataTransferObjects.LensTypeDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class GetLensTypesFromProductAttribute {
    private static final String DAILY_LENS_TYPE_NAME = "Daglenzen";
    private static final String BI_WEEKLY_LENS_TYPE_NAME = "Tweewekelijkse Lenzen";
    private static final String MONTHLY_LENS_TYPE_NAME = "Maandlezen";
    private static final String COLOUR_LENS_TYPE_NAME = "Kleurlenzen";
    private static final String MULTIFOCAL_LENS_TYPE_NAME = "Multifocale Lenzen";
    private static final String SILICONE_LENS_TYPE_NAME = "Silicone Hydrogel Lenzen";
    private static final String TORIC_LENS_TYPE_NAME = "Torische Lenzen";
    private static final String OTHER_LENS_TYPE_NAME = "Andere Type's";

    private static final ArrayList<String> DAILY_LENS_TYPE_KEYWORDS = new ArrayList<>(List.of("daglenzen", "1 day"));
    private static final ArrayList<String> BI_WEEKLY_LENS_TYPE_KEYWORDS = new ArrayList<>(List.of("tweewekelijkse"));
    private static final ArrayList<String> MONTHLY_LENS_TYPE_KEYWORDS = new ArrayList<>(List.of("maandlenzen"));
    static final ArrayList<String> COLOUR_LENS_TYPE_KEYWORDS = new ArrayList<>(List.of("kleurlenzen"));
    static final ArrayList<String> MULTIFOCAL_LENS_TYPE_KEYWORDS = new ArrayList<>(List.of("multifocal", "multifocaal"));
    static final ArrayList<String> SILICONE_LENS_TYPE_KEYWORDS = new ArrayList<>(List.of("silicone", "hydrogel", "silicone hydrogel"));
    static final ArrayList<String> TORIC_LENS_TYPE_KEYWORDS = new ArrayList<>(List.of("torisch"));

    private static final LensTypeDto DAILY_LES_TYPE_DATA = new LensTypeDto(DAILY_LENS_TYPE_NAME, DAILY_LENS_TYPE_KEYWORDS);
    private static final LensTypeDto BI_WEEKLY_LENS_TYPE_DATA = new LensTypeDto(BI_WEEKLY_LENS_TYPE_NAME, BI_WEEKLY_LENS_TYPE_KEYWORDS);
    private static final LensTypeDto MONTHLY_LENS_TYPE_DATA = new LensTypeDto(MONTHLY_LENS_TYPE_NAME, MONTHLY_LENS_TYPE_KEYWORDS);
    private static final LensTypeDto COLOUR_LENS_TYPE_DATA = new LensTypeDto(COLOUR_LENS_TYPE_NAME, COLOUR_LENS_TYPE_KEYWORDS);
    private static final LensTypeDto MULTIFOCAL_LENS_TYPE_DATA = new LensTypeDto(MULTIFOCAL_LENS_TYPE_NAME, MULTIFOCAL_LENS_TYPE_KEYWORDS);
    private static final LensTypeDto SILICONE_LENS_TYPE_DATA = new LensTypeDto(SILICONE_LENS_TYPE_NAME, SILICONE_LENS_TYPE_KEYWORDS);
    private static final LensTypeDto TORIC_LENS_TYPE_DATA = new LensTypeDto(TORIC_LENS_TYPE_NAME, TORIC_LENS_TYPE_KEYWORDS);


    static final ArrayList<LensTypeDto> LENS_TYPES = new ArrayList<>(List.of(
            DAILY_LES_TYPE_DATA,
            BI_WEEKLY_LENS_TYPE_DATA,
            MONTHLY_LENS_TYPE_DATA,
            COLOUR_LENS_TYPE_DATA,
            MULTIFOCAL_LENS_TYPE_DATA,
            SILICONE_LENS_TYPE_DATA,
            TORIC_LENS_TYPE_DATA
    ));


    public ArrayList<String> getLensTypes(String productAttribute){
        ArrayList<String> lensTypes = new ArrayList<>();

        LENS_TYPES.forEach(lensType -> {
            String lensTypeName = lensType.name();
            lensType.keywords().forEach(keyword -> {
                if(productAttribute.toLowerCase().contains(keyword)){
                    lensTypes.add(lensTypeName);
                }
            });
        });
        if(lensTypes.isEmpty()){
            lensTypes.add(OTHER_LENS_TYPE_NAME);
        }

        return lensTypes;
    }
}
