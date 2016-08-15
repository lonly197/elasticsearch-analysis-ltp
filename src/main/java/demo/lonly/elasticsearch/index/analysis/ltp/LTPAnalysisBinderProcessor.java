package demo.lonly.elasticsearch.index.analysis.ltp;

import org.elasticsearch.index.analysis.AnalysisModule.AnalysisBinderProcessor;


public class LTPAnalysisBinderProcessor extends AnalysisBinderProcessor {

    /**
     * 向bindings添加自定义的provider
     * @param analyzersBindings
     */
    @Override
    public void processAnalyzers(AnalyzersBindings analyzersBindings) {
        analyzersBindings.processAnalyzer(LTPAnalyzerProvider.NAME, LTPAnalyzerProvider.class);
    }

    /**
     *
     * @param tokenizersBindings
     */
    @Override
    public void processTokenizers(TokenizersBindings tokenizersBindings) {
        tokenizersBindings.processTokenizer(LTPTokenizerFactory.NAME, LTPTokenizerFactory.class);
    }
        
}
