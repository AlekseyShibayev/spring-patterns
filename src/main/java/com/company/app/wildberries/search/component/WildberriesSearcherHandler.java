package com.company.app.wildberries.search.component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import com.company.app.telegram.controller.TelegramController;
import com.company.app.wildberries.search.component.data.WildberriesSearcherContext;
import com.company.app.wildberries.search.component.data.WildberriesSearcherResult;
import com.company.app.wildberries.search.component.data.WildberriesSearcherTask;
import com.company.app.wildberries.search.domain.entity.SearchData;
import com.company.app.wildberries.search.domain.service.SearchDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class WildberriesSearcherHandler {

    private ExecutorService executorService;
    private Semaphore semaphore;

    private final SearchDataService searchDataService;
    private final WildberriesSearcher wildberriesSearcher;
    private final TelegramController telegramController;

    @PostConstruct
    private void init() {
        executorService = Executors.newSingleThreadExecutor();
        semaphore = new Semaphore(1);
    }

    public WildberriesSearcherResult process(WildberriesSearcherContext wildberriesSearcherContainer) {
        if (semaphore.tryAcquire()) {
            return startNewAsyncSearch(wildberriesSearcherContainer);
        } else {
            return WildberriesSearcherResult.builder()
                .isSuccess(false)
                .message("Занято! Вы что 5 лет в разработке и ни разу не использовали семафор???")
                .build();
        }
    }

    private WildberriesSearcherResult startNewAsyncSearch(WildberriesSearcherContext wildberriesSearcherContainer) {
        String chatName = wildberriesSearcherContainer.getChatName();
        SearchData searchData = searchDataService.getSearchData(chatName);
        if (searchData == null) {
            callback();
            return WildberriesSearcherResult.builder()
                .isSuccess(false)
                .message("Нет информации о поиске, обратитесь к админу.")
                .build();
        } else {
            WildberriesSearcherContext container = WildberriesSearcherContext.of(wildberriesSearcherContainer, searchData);
            log.debug("Запускаю поиск для [{}].", wildberriesSearcherContainer);
            executorService.submit(WildberriesSearcherTask.builder()
                .wildberriesSearcherContainer(container)
                .telegramController(telegramController)
                .wildberriesSearcher(wildberriesSearcher)
                .callBack(this::callback)
                .build());
            return WildberriesSearcherResult.builder().isSuccess(true).build();
        }
    }

    private void callback() {
        semaphore.release();
    }

}
