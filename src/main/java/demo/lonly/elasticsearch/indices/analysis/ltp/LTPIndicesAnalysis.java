package demo.lonly.elasticsearch.indices.analysis.ltp;

import com.mashape.unirest.http.exceptions.UnirestException;
import demo.lonly.stp.lucene.LTPAnalyzer;
import demo.lonly.stp.lucene.LTPTokenizer;
import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.component.AbstractComponent;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.analysis.AnalyzerScope;
import org.elasticsearch.index.analysis.PreBuiltAnalyzerProviderFactory;
import org.elasticsearch.index.analysis.PreBuiltTokenizerFactoryFactory;
import org.elasticsearch.index.analysis.TokenizerFactory;
import org.elasticsearch.indices.analysis.IndicesAnalysisService;
import org.json.JSONException;

import java.io.IOException;


/**
 * Registers indices level analysis components.
 */
public class LTPIndicesAnalysis extends AbstractComponent {

    private String TAG_URL;

    @Inject
    public LTPIndicesAnalysis(final Settings settings, IndicesAnalysisService indicesAnalysisService) {
        super(settings);
        // Get all the arguments from settings
        this.TAG_URL = settings.get("TAG_URL", "").toString();

        // Register the ltp type analyzer
        indicesAnalysisService.analyzerProviderFactories().put("ltp",
                new PreBuiltAnalyzerProviderFactory("ltp", AnalyzerScope.GLOBAL,
                        new LTPAnalyzer(TAG_URL)));

        // Register the ltp type tokenizer
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
                            token = new LTPTokenizer(TAG_URL);
                        } catch (JSONException | IOException | UnirestException e) {

                            e.printStackTrace();
                        }
                        return token;
                    }

                }));

    }

}
