package com.scalablecapital.currencyapi.controller;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

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
}
