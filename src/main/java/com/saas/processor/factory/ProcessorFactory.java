package com.saas.processor.factory;

import com.saas.services.ProcessorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Log4j2
public class ProcessorFactory {

    private final Map<String, ProcessorService> processorMap;

    public ProcessorFactory(List<ProcessorService> processors) {
        processorMap = processors.stream()
                .collect(Collectors.toMap(ProcessorService::getAction, Function.identity()));
    }

    public ProcessorService getProcessor(String action){
        log.info("processing action: {}", action);
        return processorMap.get(action);
    }
}
