package com.jessalva.luceneanalyzerservice.analyze;

import java.util.List;
import java.util.Map;

public record AnalyzeResponse (List<String> tokens, Map<String, List<String>> localeBasedAnalyzedTokens){
}
