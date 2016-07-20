/**
 * 
 */
package demo.lonly.stp.cfg;

import demo.lonly.elasticsearch.plugin.analysis.ltp.LTPAnalysisPlugin;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.io.PathUtils;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;
import org.elasticsearch.env.Environment;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Properties;

public class Configuration {

	private static String FILE_NAME = "LTPAnalyzer.cfg.xml";
	private static final String TAG_URL = "tag_url";
	private static ESLogger logger = Loggers.getLogger("ltp-analyzer");
	private Path conf_dir;
	private Properties props;
	private Environment environment;

	@Inject
	public Configuration(Environment env) {
		props = new Properties();
		environment = env;

		conf_dir = environment.configFile().resolve(LTPAnalysisPlugin.PLUGIN_NAME);
		Path configFile = conf_dir.resolve(FILE_NAME);

		InputStream input = null;
		try {
			logger.info("try load config from {}", configFile);
			input = new FileInputStream(configFile.toFile());
		} catch (FileNotFoundException e) {
			conf_dir = this.getConfigInPluginDir();
			configFile = conf_dir.resolve(FILE_NAME);
			try {
				logger.info("try load config from {}", configFile);
				input = new FileInputStream(configFile.toFile());
			} catch (FileNotFoundException ex) {
				// We should report origin exception
				logger.error("ltp-analyzer", e);
			}
		}
		if (input != null) {
			try {
				props.loadFromXML(input);
			} catch (InvalidPropertiesFormatException e) {
				logger.error("ltp-analyzer", e);
			} catch (IOException e) {
				logger.error("ltp-analyzer", e);
			}
		}
	}



	public String getTagUrl() {
		String tagurl = props.getProperty(TAG_URL,"");
		return tagurl;
	}

	public String getDictRoot() {
		return conf_dir.toAbsolutePath().toString();
	}

	private Path getConfigInPluginDir() {
		return PathUtils
				.get(new File(LTPAnalysisPlugin.class.getProtectionDomain().getCodeSource().getLocation().getPath())
						.getParent(), "config")
				.toAbsolutePath();
	}
}
