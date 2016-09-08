package demo.lonly.stp.dict;

import com.google.common.collect.Sets;
import demo.lonly.stp.cfg.StaticField;
import demo.lonly.stp.util.IOUtil;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

/**
 * 停用词字典
 * Created by Lonly on 2016/9/8.
 */
public class StopDict {

    public static Set<String> filters = Sets.newHashSet();
    private static ESLogger logger = Loggers.getLogger("ltp-stopdict");

    /**
     * 加载停用词
     *
     * @param stopLibraryPath
     */
    public static Set<String> loadDict(String stopLibraryPath, Settings settings,
        Environment environment) {
        if (Strings.isNullOrEmpty(stopLibraryPath)) {
            stopLibraryPath = settings.get("stop_path", StaticField.DEFAULT_STOP_FILE_LIB_PATH);
            logger.info("stop_path:{}", stopLibraryPath);
        }
        File stopLibrary;
        if (Files.exists(Paths.get(stopLibraryPath))) {
            stopLibrary = new File(stopLibraryPath);
        } else {
            stopLibrary =
                new File(environment.pluginsFile().toFile(),
                    StaticField.DEFAULT_STOP_FILE_LIB_PATH);
        }
        logger.info("停用词典路径:{}", stopLibrary.getAbsolutePath());
        if (!stopLibrary.isFile()) {
            logger
                .info("Can't find the file:{}, no such file or directory exists!", stopLibraryPath);
        } else {
            try (BufferedReader br = IOUtil.getReader(stopLibrary.getAbsolutePath(), "UTF-8")) {
                String temp;
                while ((temp = br.readLine()) != null) {
                    filters.add(temp);
                }
            } catch (IOException e) {
                logger.info("ltp analyzer停用词典加载出错!");
            }
            logger.info("ltp analyzer停止词典加载完毕!");
        }
        return filters;
    }
}
