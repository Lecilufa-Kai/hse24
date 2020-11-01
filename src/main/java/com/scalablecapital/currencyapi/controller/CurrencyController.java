package com.scalablecapital.currencyapi.controller;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalablecapital.currencyapi.dto.Category;
import com.scalablecapital.currencyapi.dto.CurrencyConversionDto;
import com.scalablecapital.currencyapi.dto.ReferenceRateDto;
import com.scalablecapital.currencyapi.entity.Currency;
import com.scalablecapital.currencyapi.service.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/exchange-rate-service")
public class CurrencyController {

    private final CurrencyService currencyService;

    //Constructor injection don't need @Autowired, inject dependencies by spring boot
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping(value = "currencies/reference-rate", produces = {"application/json"})
    public ResponseEntity<ReferenceRateDto> currenciesReferenceRate(
        @Pattern(regexp = "[A-Z]{3}", message = "source currency should be 3 Capital letters") @RequestParam String source,
        @Pattern(regexp = "[A-Z]{3}", message = "target currency should be 3 Capital letters") @RequestParam String target
    ) {
        return ResponseEntity.ok().body(currencyService.getCurrenciesReferenceRate(source, target));
    }

    @GetMapping(value = "currencies/convert-rate", produces = {"application/json"})
    public ResponseEntity<CurrencyConversionDto> convertCurrencies(
        @Pattern(regexp = "[A-Z]{3}", message = "source currency should be 3 Capital letters") @RequestParam String source,
        @RequestParam @Min(0) double sourceAmount,
        @Pattern(regexp = "[A-Z]{3}", message = "target currency should be 3 Capital letters") @RequestParam String target
    ) {
        return ResponseEntity.ok().body(currencyService.getCurrencyConversion(source, sourceAmount, target));
    }

    @GetMapping(value = "currencies", produces = {"application/json"})
    public ResponseEntity<List<Currency>> getAllCurrencies() {
        return ResponseEntity.ok().body(currencyService.getAllCurrencies());
    }

    @GetMapping(value = "testData", produces = {"application/json"})
    public ResponseEntity<List<Category>> getCategory() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        Category[] result = mapper.readValue(getFileFromResource("aa.json"),Category[].class);

        return ResponseEntity.ok().body(Arrays.asList(result));
    }

    private File getFileFromResource(String fileName) throws URISyntaxException {

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {

            // failed if files have whitespaces or special characters
            //return new File(resource.getFile());

            return new File(resource.toURI());
        }

    }
}
