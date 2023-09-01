package com.company.app.wildberries_desire_lot.component.data;

import com.company.app.wildberries_desire_lot.domain.entity.DesireLot;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class WildberriesDesireLotUrlCreatorTest {

    @Test
    void getUrlForPriceSearch() {
        String urlForPriceSearch = WildberriesDesireLotUrlCreator.getUrlForPriceSearch(createLots());
        String manualCreated = WildberriesDesireLotUrlCreator.URL_BONE + "43409221;" + "15694225;";
        Assertions.assertEquals(urlForPriceSearch, manualCreated);
    }

    private List<DesireLot> createLots() {
        return ImmutableList.<DesireLot>builder()
                .add(new DesireLot().setArticle("43409221").setDesiredPrice("1500").setDiscount("0.19"))
                .add(new DesireLot().setArticle("43409221").setDesiredPrice("1500").setDiscount("0.19"))
                .add(new DesireLot().setArticle("15694225").setDesiredPrice("5500").setDiscount("0.17"))
                .add(new DesireLot().setArticle("15694225").setDesiredPrice("5500").setDiscount("0.17"))
                .build();
    }

}