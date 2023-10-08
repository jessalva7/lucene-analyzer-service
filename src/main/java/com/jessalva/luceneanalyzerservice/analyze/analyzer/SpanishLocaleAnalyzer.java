package com.jessalva.luceneanalyzerservice.analyze.analyzer;

import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.analysis.fr.FrenchAnalyzer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SpanishLocaleAnalyzer extends AnalyzerBase {


    @Override
    public List<String> analyze(String input) {
        final List<String> analyzedTokens = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        try (SpanishAnalyzer spanishAnalyzer = new SpanishAnalyzer()) {
            analyzeText(input, analyzedTokens, spanishAnalyzer);
        }
        System.out.printf(
                "Time taken to analyze: %d\n",System.currentTimeMillis()-startTime);
        return analyzedTokens;
    }



    @Override
    public List<String> supportedLocales() {
        return List.of("es-ES", "es-MX", "es-US");
    }

    @Override
    public String analyzerName() {
        return "SpanishAnalyzer";
    }
}
