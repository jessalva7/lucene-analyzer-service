package com.jessalva.luceneanalyzerservice.analyze.analyzer;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.de.GermanAnalyzer;
import org.apache.lucene.analysis.fr.FrenchAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class FrenchLocaleAnalyzer extends AnalyzerBase {


    @Override
    public List<String> analyze(String input) {
        final List<String> analyzedTokens = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        try (FrenchAnalyzer frenchAnalyzer = new FrenchAnalyzer()) {
            analyzeText(input, analyzedTokens, frenchAnalyzer);
        }
        System.out.printf(
                "Time taken to analyze: %d\n",System.currentTimeMillis()-startTime);
        return analyzedTokens;
    }



    @Override
    public List<String> supportedLocales() {
        return List.of("fr-FR", "fr-CA");
    }

    @Override
    public String analyzerName() {
        return "FrenchAnalyzer";
    }
}
