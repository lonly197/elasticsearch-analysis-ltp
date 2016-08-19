package demo.lonly.elasticsearch.index.analysis.ltp;

import demo.lonly.stp.cfg.Configuration;
import demo.lonly.stp.lucene.LTPAnalyzer;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.analysis.AbstractIndexAnalyzerProvider;
import org.elasticsearch.index.settings.IndexSettingsService;

/**
 * 封装分析器
 * 与ElasticSearch集成，分词器的配置均从ElasticSearch的配置文件读取，因此，需要重载Analyzer的构造方法，然后继承ElasticSearch的类AbstractIndexAnalyzerProvider
 */
public class LTPAnalyzerProvider extends AbstractIndexAnalyzerProvider<LTPAnalyzer> {
    private final LTPAnalyzer analyzer;
    private String TAG_URL;


    /*
     * Name to associate with this class. It will be used in BinderProcesser
     */
    public static final String NAME = "ltp";

    @Inject
    public LTPAnalyzerProvider(Index index, IndexSettingsService indexSettingsService, Environment env, @Assisted String name, @Assisted Settings settings, Settings settings1) {

        super(index, indexSettingsService.getSettings(),name, settings);
        this.analyzer = new LTPAnalyzer();
        // 插件私有配置初始化
        //Configuration.init(settings,env);
    }

    @Override
    public LTPAnalyzer get() {
        return this.analyzer;
    }
}
