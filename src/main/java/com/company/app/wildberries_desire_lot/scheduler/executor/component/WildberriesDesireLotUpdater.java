package com.company.app.wildberries_desire_lot.scheduler.executor.component;

import com.company.app.core.util.Collections;
import com.company.app.wildberries_desire_lot.domain.entity.DesireLot;
import com.company.app.wildberries_desire_lot.domain.repository.DesireLotRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class WildberriesDesireLotUpdater {

    private final DesireLotRepository desireLotRepository;

    public List<DesireLot> updateDesireLots(List<DesireLot> dtoListToUpdate) {
        List<DesireLot> persisted = desireLotRepository.findAll();
        if (Collections.isEmpty(persisted)) {
            return desireLotRepository.saveAll(dtoListToUpdate);
        } else {
            Map<String, DesireLot> desireLotPersistedMap = persisted.stream()
                    .collect(Collectors.toMap(DesireLot::getArticle, Function.identity()));

            List<DesireLot> result = new ArrayList<>();
            for (DesireLot toUpdate : dtoListToUpdate) {
                if (desireLotPersistedMap.containsKey(toUpdate.getArticle())) {
                    DesireLot persistedDesireLot = desireLotPersistedMap.get(toUpdate.getArticle());
                    persistedDesireLot.setPrice(toUpdate.getPrice());
                    persistedDesireLot.setDescription(toUpdate.getDescription());
                    result.add(desireLotRepository.save(persistedDesireLot));
                } else {
                    result.add(desireLotRepository.save(toUpdate));
                }
            }
            return result;
        }
    }

}
