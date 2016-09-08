package demo.lonly.elasticsearch.indices.analysis.ltp;

import com.mashape.unirest.http.exceptions.UnirestException;
import demo.lonly.stp.cfg.Configuration;
import demo.lonly.stp.lucene.LTPAnalyzer;
import demo.lonly.stp.lucene.LTPTokenizer;
import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.component.AbstractComponent;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.ESLoggerFactory;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.analysis.AnalyzerScope;
import org.elasticsearch.index.analysis.PreBuiltAnalyzerProviderFactory;
import org.elasticsearch.index.analysis.PreBuiltTokenizerFactoryFactory;
import org.elasticsearch.index.analysis.TokenizerFactory;
import org.elasticsearch.indices.analysis.IndicesAnalysisService;
import org.json.JSONException;

import java.io.IOException;


/**
 * 注册indices level analysis components.
 */
public class LTPIndicesAnalysis extends AbstractComponent {

    private static final ESLogger logger = ESLoggerFactory.getLogger("LTPIndicesAnalysis");

    @Inject
    public LTPIndicesAnalysis(final Settings settings, IndicesAnalysisService indicesAnalysisService, Environment env) {
        super(settings);

        logger.info("LTPIndicesAnalysis Initialize......");

        // 插件私有配置初始化
        Configuration.init(settings,env);

        // 注册ltp type analyzer
        indicesAnalysisService.analyzerProviderFactories().put("ltp",
                new PreBuiltAnalyzerProviderFactory("ltp", AnalyzerScope.GLOBAL,
                        new LTPAnalyzer()));

        // 注册ltp type tokenizer
        indicesAnalysisService.tokenizerFactories().put("ltp",
                new PreBuiltTokenizerFactoryFactory(new TokenizerFactory() {

                    @Override
                    public String name() {
                        return "ltp";
                    }

                    @Override
                    public Tokenizer create() {
                        LTPTokenizer token = null;
                        try {
                            token = new LTPTokenizer(Configuration.getFilters());
                        } catch (JSONException | IOException | UnirestException e) {

                            e.printStackTrace();
                        }
                        return token;
                    }

                }));

    }

}
