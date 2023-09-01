package com.company.app.exchange_rate;

import com.company.app.core.aop.logging.performance.PerformanceLogAnnotation;
import com.company.app.exchange_rate.component.ExchangeRateExtractor;
import com.company.app.exchange_rate.domain.entity.ExchangeRate;
import com.company.app.exchange_rate.domain.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRateFacade {

    @Autowired
    private ExchangeRateExtractor exchangeRateExtractor;
    @Autowired
    private ExchangeRateService exchangeRateService;

    @PerformanceLogAnnotation
    public ExchangeRate extract() {
        return exchangeRateExtractor.extract();
    }

    @PerformanceLogAnnotation
    public ExchangeRate getLast() {
        return exchangeRateService.getLast();
    }
}
