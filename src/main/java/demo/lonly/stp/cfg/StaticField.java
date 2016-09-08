package demo.lonly.stp.cfg;

/**
 * Created by Lonly on 2016/9/8.
 */
public class StaticField {

    /**
     * 是否本地模式
     */
    public static final String IS_LOCAL = "is_local";
    /**
     * LTP Model目录地址
     */
    public static final String MODEL_PATH = "model_path";
    /**
     * 非Local模式时，LTP Restful API URL
     */
    public static final String API_URL = "api_url";
    /**
     * 提用词字典路径
     */
    public static final String STOP_PATH = "stop_path";
    /**
     * 默认停用词字典路径
     */
    public final static String DEFAULT_STOP_FILE_LIB_PATH = "ltp/dic/stopLibrary.dic";
    /**
     * Config配置文件名称
     */
    public static String Config_FILE_NAME = "LTPAnalyzer.cfg.xml";
}
