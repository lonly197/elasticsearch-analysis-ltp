package demo.lonly.elasticsearch.index.analysis.ltp;

import demo.lonly.stp.lucene.LTPAnalyzer;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.analysis.AbstractIndexAnalyzerProvider;
import org.elasticsearch.index.settings.IndexSettingsService;

public class LTPAnalyzerProvider extends AbstractIndexAnalyzerProvider<LTPAnalyzer> {
    private final LTPAnalyzer analyzer;
    private String TAG_URL;


    /*
     * Name to associate with this class. It will be used in BinderProcesser
     */
    public static final String NAME = "ltp";

    @Inject
    public LTPAnalyzerProvider(Index index, IndexSettingsService indexSettingsService, Environment env, @Assisted String name, @Assisted Settings settings, Settings settings1) {

        super(index, indexSettingsService.getSettings(), name, settings);
        this.TAG_URL = settings.get("TAG_URL", "").toString();

        this.analyzer = new LTPAnalyzer(TAG_URL);

    }

    @Override
    public LTPAnalyzer get() {
        return this.analyzer;
    }
}
