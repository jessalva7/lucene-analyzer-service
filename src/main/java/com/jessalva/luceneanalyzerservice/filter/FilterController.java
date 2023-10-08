package com.jessalva.luceneanalyzerservice.filter;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ngram.NGramTokenFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FilterController {

    @PostMapping("/filter")
    public FilterResponse filter(@RequestBody FilterRequest filterRequest,
                                 @RequestParam(value = "filterType", required = false) String filterType) {
        final List<String> analyzedTokens = new ArrayList<>();
        System.out.printf("analyzer is empty {%s}", filterType);
        try
        {
            StandardTokenizer standardTokenizer = new StandardTokenizer();
            standardTokenizer.setReader(new StringReader(filterRequest.input()));
            TokenStream tokenStream  = new NGramTokenFilter(standardTokenizer, 3);
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                analyzedTokens.add(tokenStream.getAttribute(CharTermAttribute.class).toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new FilterResponse(analyzedTokens);
    }

}
