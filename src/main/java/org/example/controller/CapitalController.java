package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CapitalController {

    private final Map<String, String> capitalsToCountriesMap = new HashMap<>();

    public CapitalController() {
        capitalsToCountriesMap.put("Paris", "France");
        capitalsToCountriesMap.put("Washington DC", "United States");
        capitalsToCountriesMap.put("London", "United Kingdom");
    }

    @GetMapping("/capital/{capitalName}")
    public String getCountryByCapitalName(@PathVariable String capitalName) {
        String country = capitalsToCountriesMap.get(capitalName);
        if (country != null) {
            return "The country for " + capitalName + " is " + country;
        } else {
            return "No country found for " + capitalName;
        }
    }
}
