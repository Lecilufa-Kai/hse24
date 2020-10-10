package com.scalablecapital.currencyapi.event;

import com.scalablecapital.currencyapi.db.DataHolder;
import com.scalablecapital.currencyapi.entity.Currency;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

        /*
        USD	US dollar	1.1795	USD
        JPY	Japanese yen	124.95	JPY
        BGN	Bulgarian lev	1.9558	BGN
        CZK	Czech koruna	27.110	CZK
        DKK	Danish krone	7.4422	DKK
        GBP	Pound sterling	0.91167	GBP
        HUF	Hungarian forint	356.28	HUF
         */
        Currency USD = new Currency(1,"US dollar","USD",1.1795,0L);
        Currency JPY = new Currency(2,"Japanese yen","JPY",124.95,0L);
        Currency BGN = new Currency(3,"Bulgarian lev","BGN",1.9558,0L);
        Currency CZK = new Currency(4,"Czech koruna","CZK",27.110,0L);
        Currency DKK = new Currency(5,"Danish krone","DKK",7.4422,0L);
        Currency GBP = new Currency(6,"Pound sterling","GBP",0.91167,0L);
        Currency HUF = new Currency(7,"Hungarian forint","HUF",356.28,0L);
        Currency EUR = new Currency(8,"Europe","EUR",1,0L);

        DataHolder.currencyDB.add(USD);
        DataHolder.currencyDB.add(JPY);
        DataHolder.currencyDB.add(BGN);
        DataHolder.currencyDB.add(CZK);
        DataHolder.currencyDB.add(DKK);
        DataHolder.currencyDB.add(GBP);
        DataHolder.currencyDB.add(HUF);
        DataHolder.currencyDB.add(EUR);
    }
}
