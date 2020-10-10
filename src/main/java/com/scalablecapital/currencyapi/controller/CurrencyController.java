package com.scalablecapital.currencyapi.controller;

import com.scalablecapital.currencyapi.entity.Currency;
import com.scalablecapital.currencyapi.dto.CurrencyConversionDto;
import com.scalablecapital.currencyapi.dto.ReferenceRateDto;
import com.scalablecapital.currencyapi.service.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.List;


@Validated
@RestController
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("currencies/reference-rate")
    public ResponseEntity<ReferenceRateDto> currenciesReferenceRate(
            @Pattern(regexp = "[A-Z]{3}" ,message = "source currency should be 3 Capital letters") @RequestParam String source,
            @Pattern(regexp = "[A-Z]{3}" ,message = "target currency should be 3 Capital letters") @RequestParam String target
    ){
        return ResponseEntity.ok().body(currencyService.currenciesReferenceRate(source, target));
    }

    @GetMapping("currencies/convert-rate")
    public ResponseEntity<CurrencyConversionDto> convertCurrencies(
            @Pattern(regexp = "[A-Z]{3}" ,message = "source currency should be 3 Capital letters") @RequestParam String source,
            @RequestParam @Min(0) Double sourceAmount,
            @Pattern(regexp = "[A-Z]{3}" ,message = "target currency should be 3 Capital letters") String target
    ){
        CurrencyConversionDto response = new CurrencyConversionDto();
        response.setSource(source);
        response.setSourceAmount(sourceAmount);
        response.setTarget(target);
        response.setTargetAmount(123.0);

        return ResponseEntity.ok().body(response);
    }


    @GetMapping("currencies")
    public ResponseEntity<List<Currency>> getAllCurrencies(){
        return ResponseEntity.ok().body(currencyService.getAllCurrencies());
    }
}
