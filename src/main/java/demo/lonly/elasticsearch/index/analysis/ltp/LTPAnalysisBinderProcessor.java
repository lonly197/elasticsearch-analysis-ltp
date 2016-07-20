package demo.lonly.elasticsearch.index.analysis.ltp;

import org.elasticsearch.index.analysis.AnalysisModule.AnalysisBinderProcessor;


public class LTPAnalysisBinderProcessor extends AnalysisBinderProcessor {

    /*
     * It simply adds our analyzer provider class to a list of bindings.
     */
    @Override
    public void processAnalyzers(AnalyzersBindings analyzersBindings) {
        analyzersBindings.processAnalyzer(LTPAnalyzerProvider.NAME, LTPAnalyzerProvider.class);
    }

    @Override
    public void processTokenizers(TokenizersBindings tokenizersBindings) {
        tokenizersBindings.processTokenizer(LTPTokenizerFactory.NAME, LTPTokenizerFactory.class);
    }
        
}
