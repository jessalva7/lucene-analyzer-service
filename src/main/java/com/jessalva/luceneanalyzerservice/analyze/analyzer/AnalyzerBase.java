package com.jessalva.luceneanalyzerservice.analyze.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.fr.FrenchAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.util.List;

public abstract class AnalyzerBase {
    abstract public List<String> analyze(String input);

    abstract public List<String> supportedLocales();

    abstract public String analyzerName();

    protected void analyzeText(String input, List<String> analyzedTokens, Analyzer analyzer) {
        TokenStream tokenStream = analyzer.tokenStream("text", input);
        CharTermAttribute token = tokenStream.addAttribute(CharTermAttribute.class);
        try {
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                analyzedTokens.add(token.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
