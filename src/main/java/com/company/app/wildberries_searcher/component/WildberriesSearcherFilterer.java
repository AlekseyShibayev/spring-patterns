package com.company.app.wildberries_searcher.component;

import com.company.app.core.aop.logging.performance.PerformanceLogAnnotation;
import com.company.app.core.util.Logs;
import com.company.app.infrastructure.data.ResponseProducts;
import com.company.app.wildberries_searcher.component.data.WildberriesSearcherContext;
import com.company.app.wildberries_searcher.component.filter.WildberriesSearcherFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class WildberriesSearcherFilterer {

    /**
     * Нужно для логов. Будет только один поток, гарантирую.
     */
    private int preparedProductsSize;
    private int filterPosition;

    private final WildberriesSearcherAveragePriceExtractor wildberriesSearcherAveragePriceExtractor;
    private final WildberriesSearcherNotificator wildberriesSearcherNotificator;
    private final List<WildberriesSearcherFilter> wildberriesSearcherFilterList;

    @PerformanceLogAnnotation
    public List<ResponseProducts> filter(List<ResponseProducts> products, WildberriesSearcherContext wildberriesSearcherContainer) {
        List<ResponseProducts> preparedProducts = products.stream()
                .filter(responseProducts -> filterAll(responseProducts, wildberriesSearcherContainer))
                .toList();

        if (log.isDebugEnabled()) {
            log.debug("[{}]: После предварительной фильтрации осталось [{}] шт.", wildberriesSearcherContainer.getChatName(), preparedProducts.size());
            preparedProductsSize = preparedProducts.size();
            filterPosition = 1;
        }

        return preparedProducts.stream()
                .filter(responseProducts -> currentPriceLesserThanAveragePrice(responseProducts, wildberriesSearcherContainer))
                .map(responseProducts -> notify(responseProducts, wildberriesSearcherContainer))
                .toList();
    }

    private ResponseProducts notify(ResponseProducts responseProducts, WildberriesSearcherContext wildberriesSearcherContainer) {
        wildberriesSearcherNotificator.notify(responseProducts, wildberriesSearcherContainer);
        return responseProducts;
    }

    private boolean filterAll(ResponseProducts responseProducts, WildberriesSearcherContext wildberriesSearcherContainer) {
        for (WildberriesSearcherFilter filter : wildberriesSearcherFilterList) {
            if (filter.isPreFilter()) {
                boolean result = filter.doFilter(responseProducts, wildberriesSearcherContainer);
                if (!result) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean currentPriceLesserThanAveragePrice(ResponseProducts responseProducts, WildberriesSearcherContext wildberriesSearcherContainer) {
        try {
            BigDecimal averagePrice = new BigDecimal(wildberriesSearcherAveragePriceExtractor.getAveragePrice(responseProducts));
            BigDecimal currentPrice = new BigDecimal(responseProducts.getSalePriceU());
            currentPrice = currentPrice.multiply(new BigDecimal(wildberriesSearcherContainer.getGreedIndex()));
            doLog(wildberriesSearcherContainer, responseProducts, averagePrice, currentPrice);
            int i = currentPrice.compareTo(averagePrice);
            return i < 0;
        } catch (Exception exception) {
            Logs.doExceptionLog(log, exception, "[%s] Проблема с [%s]:".formatted(wildberriesSearcherContainer.getChatName(), responseProducts.getId()));
            return false;
        }
    }

    private void doLog(WildberriesSearcherContext wildberriesSearcherContainer, ResponseProducts responseProducts, BigDecimal averagePrice, BigDecimal currentPrice) {
        if (log.isDebugEnabled()) {
            log.debug("[{}]: [{}]: Цена: текущая*[{}]: [{}] < средняя: [{}] ? [{}/{}]",
                    wildberriesSearcherContainer.getChatName(),
                    responseProducts.getId(),
                    wildberriesSearcherContainer.getGreedIndex(),
                    currentPrice,
                    averagePrice,
                    filterPosition,
                    preparedProductsSize
            );
            filterPosition++;
        }
    }
}