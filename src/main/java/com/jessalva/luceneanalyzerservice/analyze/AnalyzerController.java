package com.jessalva.luceneanalyzerservice.analyze;

import com.jessalva.luceneanalyzerservice.analyze.analyzer.AnalyzerFactory;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class AnalyzerController {

    @Autowired
    AnalyzerFactory analyzerFactory;

    @PostMapping("/analyze")
    public AnalyzeResponse analyze(@RequestBody AnalyzeRequest analyzeRequest,
                                   @RequestParam(value = "analyzerType",required = false) String analyzerType) {
        final List<String> analyzedTokens = new ArrayList<>();
        final Map<String, List<String>> localeBasedAnalyzedTokens;
        try (StandardAnalyzer standardAnalyzer = new StandardAnalyzer()) {
            TokenStream tokenStream = standardAnalyzer.tokenStream("text", analyzeRequest.input());
            CharTermAttribute token = tokenStream.addAttribute(CharTermAttribute.class);
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                analyzedTokens.add(token.toString());
            }
            localeBasedAnalyzedTokens = analyzerFactory.analyze(analyzeRequest.locale(), analyzeRequest.input());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new AnalyzeResponse(analyzedTokens, localeBasedAnalyzedTokens);
    }

}
