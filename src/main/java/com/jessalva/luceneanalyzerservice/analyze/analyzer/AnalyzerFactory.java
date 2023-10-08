package com.jessalva.luceneanalyzerservice.analyze.analyzer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class AnalyzerFactory {

    final Map<String, List<AnalyzerBase>> localeAnalyzerMap;

    @Autowired
    public AnalyzerFactory(List<AnalyzerBase> iAnalyzerList) {
        this.localeAnalyzerMap = new HashMap<>();
        iAnalyzerList
                .forEach(
                        analyzer -> analyzer.supportedLocales()
                                    .forEach(
                                            locale -> {
                                                localeAnalyzerMap.putIfAbsent(
                                                        locale, new ArrayList<>()
                                                );
                                                localeAnalyzerMap.get(
                                                        locale
                                                ).add(analyzer);
                                            }
                                    )
                );
    }


    public Map<String, List<String>> analyze(String locale, String input)
    {
        List<AnalyzerBase> analyzers = Optional.ofNullable(localeAnalyzerMap.get(locale))
                .orElse(new ArrayList<>());
        if(CollectionUtils.isEmpty(analyzers))
        {
            return new HashMap<>();
        }

        return analyzers.stream().collect(
                Collectors.toMap(
                        AnalyzerBase::analyzerName, analyzer -> analyzer.analyze(input)
                )
        );

    }

}
