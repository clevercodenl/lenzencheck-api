package com.lenzencheckapi.LenzencheckAPI.DataTransferObjects;

import java.util.ArrayList;

public record LensTypeDto(String name, ArrayList<String> keywords) {
}
