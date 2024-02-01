package com.lenzencheckapi.LenzencheckAPI.Controllers;

import com.lenzencheckapi.LenzencheckAPI.Responses.GetMetaDataResponse;
import org.springframework.web.bind.annotation.*;

import java.time.Year;

@RestController
public class GetMetaDataController {
    private static final String PHONE_NUMBER = "+31646545465";
    private static final String METADATA_ENDPOINT = "/metadata";

    @GetMapping(METADATA_ENDPOINT)
    @ResponseBody
    public GetMetaDataResponse getMetaData() {
        String currentYearAsString = String.valueOf(Year.now().getValue());

        return new GetMetaDataResponse(currentYearAsString, PHONE_NUMBER);
    }
}
