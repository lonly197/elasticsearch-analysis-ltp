package demo.lonly.elasticsearch.index.analysis.ltp;

import org.elasticsearch.index.analysis.AnalysisModule.AnalysisBinderProcessor;

/**
 * 绑定分析器
 * 分析器的绑定需要继承绑定处理器AnalysisModule.AnalysisBinderProcessor
 */
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
