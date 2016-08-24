/**
 *
 */
package demo.lonly.stp.cfg;

import demo.lonly.elasticsearch.plugin.analysis.ltp.LTPAnalysisPlugin;
import org.elasticsearch.common.io.PathUtils;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * 私有配置管理类
 */
public class Configuration {

    private static final String IS_LOCAL = "is_local";
    private static final String MODEL_PATH = "model_path";
    private static final String API_URL = "api_url";
    private static ESLogger logger = Loggers.getLogger("ltp-initializer");
    private static volatile boolean loaded = false;
    private static String FILE_NAME = "LTPAnalyzer.cfg.xml";
    private static Path conf_dir;
    private static Properties props;
    private static Environment environment;

    public static void init(Settings settings, Environment env) {
        if (isLoaded()) {
            return;
        }
        props = new Properties();
        environment = env;

        conf_dir = environment.configFile().resolve(LTPAnalysisPlugin.PLUGIN_NAME);
        Path configFile = conf_dir.resolve(FILE_NAME);

        InputStream input = null;
        try {
            if (Files.exists(configFile)) {
                // 尝试从elasticsearch/config目录下读取配置文件
                logger.info("try load config from {}", configFile);
                input = new FileInputStream(configFile.toFile());
            } else {
                conf_dir = getConfigInPluginDir();
                configFile = conf_dir.resolve(FILE_NAME);
                if (Files.exists(configFile)) {
                    // 尝试从plugins/ltp/config目录下读取配置文件
                    logger.info("try load config from {}", configFile);
                    input = new FileInputStream(configFile.toFile());
                } else {
                    logger.error("config file is not existed");
                }
            }
        } catch (Exception e) {
            // 记录错误信息
            logger.error("ltp-initializer", e);
        } finally {
            closeInput(input);
        }

    }

    private static void closeInput(InputStream input) {
        try {
            if (input != null)
                input.close();
        } catch (Exception e) {
            logger.error("ltp-analyzer", e);
        }
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
        return props.getProperty(IS_LOCAL, "").equals("true");
    }

    public static String getModelPath() {
        return props.getProperty(MODEL_PATH, "");
    }

    public static String getApiUrl() {
        return props.getProperty(API_URL, "");
    }
}
