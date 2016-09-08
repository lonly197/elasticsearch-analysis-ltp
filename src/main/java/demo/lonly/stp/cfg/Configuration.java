/**
 *
 */
package demo.lonly.stp.cfg;

import com.google.common.collect.Sets;
import demo.lonly.elasticsearch.plugin.analysis.ltp.LTPAnalysisPlugin;
import demo.lonly.stp.dict.StopDict;
import demo.lonly.stp.util.IOUtil;
import org.elasticsearch.common.io.PathUtils;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.Set;

/**
 * 私有配置管理类
 */
public class Configuration {

    private static ESLogger logger = Loggers.getLogger("ltp-onfiguration");
    private static volatile boolean loaded = false;

    /**
     * 过滤词c
     */
    private static Set<String> filter = Sets.newHashSet();
    /**
     * Config目录地址
     */
    private static Path conf_dir;
    /**
     * 属性
     */
    private static Properties props;
    /**
     * 环境
     */
    private static Environment environment;
    private static Settings settings;

    /**
     * 初始化
     *
     * @param set
     * @param env
     */
    public synchronized static void init(Settings set, Environment env) {
        //判断是否已经加载
        if (isLoaded()) {
            return;
        }
        logger.info("ltp-configuration init start...");
        props = new Properties();
        environment = env;
        settings = set;

        conf_dir = environment.configFile().resolve(LTPAnalysisPlugin.PLUGIN_NAME);
        Path configFile = conf_dir.resolve(StaticField.Config_FILE_NAME);

        InputStream input = null;
        try {
            if (Files.exists(configFile)) {
                // 尝试从elasticsearch/config目录下读取配置文件
                logger.info("1、try load config from {}", configFile);
                //input = new FileInputStream(configFile.toFile());
                input = IOUtil.getInputStream(configFile.toString());
            } else {
                conf_dir = getConfigInPluginDir();
                configFile = conf_dir.resolve(StaticField.Config_FILE_NAME);
                if (Files.exists(configFile)) {
                    // 尝试从plugins/ltp/config目录下读取配置文件
                    logger.info("2、try load config from {}", configFile);
                    //input = new FileInputStream(configFile.toFile());
                    input = IOUtil.getInputStream(configFile.toString());
                } else {
                    logger.error("config file is not existed");
                }
            }
        } catch (Exception e) {
            // 记录错误信息
            logger.error("ltp-configuration", e);
        } finally {
            if (input != null) {
                try {
                    props.loadFromXML(input);
                } catch (InvalidPropertiesFormatException e) {
                    logger.error("ltp-configuration", e);
                } catch (IOException e) {
                    logger.error("ltp-configuration", e);
                }
            }
            IOUtil.close(input);
        }
        intiFilter();
        logger.info("ltp-configuration init end...");
        setLoaded(true);
    }

    private static void intiFilter() {
        logger.info("ltp-filters init start...");
        // 1、优先加载LTPAnalyzer.cfg.xml的stop_path
        // 2、若1不存在，加载elasticsearch.yml的stop_path
        // 3、若2不存在，记载plugins/ltp/dic/
        String path = getStopPath();
        logger.info(String.format("ltp-filters path:%s", path));
        filter = StopDict.loadDict(path, settings, environment);
        logger.info("ltp-filters init end...");
    }

    /**
     * 获取es安装目录
     *
     * @return
     */
    public static String getRoot() {
        return conf_dir.toAbsolutePath().toString();
    }

    /**
     * 获取插件所在目录
     *
     * @return
     */
    private static Path getConfigInPluginDir() {
        return PathUtils.get(new File(
            LTPAnalysisPlugin.class.getProtectionDomain().getCodeSource().getLocation().getPath())
            .getParent(), "config").toAbsolutePath();
    }

    public static boolean isLoaded() {
        return loaded;
    }

    public static void setLoaded(boolean loaded) {
        Configuration.loaded = loaded;
    }

    public static Boolean getIsLocal() {
        return props.getProperty(StaticField.IS_LOCAL, "").equals("true");
    }

    public static String getModelPath() {
        return props.getProperty(StaticField.MODEL_PATH, "");
    }

    public static String getApiUrl() {
        return props.getProperty(StaticField.API_URL, "");
    }

    public static String getStopPath() {
        return props.getProperty(StaticField.STOP_PATH, "");
    }

    public static Set<String> getFilters() {
        return filter;
    }
}
