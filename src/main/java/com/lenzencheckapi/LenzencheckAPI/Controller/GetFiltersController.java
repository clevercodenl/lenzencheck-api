package com.lenzencheckapi.LenzencheckAPI.Controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class GetFiltersController {
    private static final String FILTERS_ENDPOINT = "/filters";
    private static final String FILTERS_FILENAME = "static/filters.json";

    @GetMapping(FILTERS_ENDPOINT)
    @ResponseBody
    public ResponseEntity<String> getProducts() {
        try {
            ClassPathResource resource = new ClassPathResource(FILTERS_FILENAME);
            Path path = Paths.get(resource.getURI());
            String jsonData = Files.readString(path);

            return ResponseEntity.ok(jsonData);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error reading JSON file");
        }
    }
}
